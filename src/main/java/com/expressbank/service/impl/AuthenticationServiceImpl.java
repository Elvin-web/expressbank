package com.expressbank.service.impl;

import com.expressbank.dto.CommonResponse;
import com.expressbank.dto.signIn.request.SignInRequest;
import com.expressbank.dto.signIn.response.SignInResponse;
import com.expressbank.dto.signUp.request.SignUpRequest;
import com.expressbank.email.EmailSender;
import com.expressbank.email.EmailValidator;
import com.expressbank.entity.ConfirmationTokenEntity;
import com.expressbank.entity.RoleEntity;
import com.expressbank.entity.UserEntity;
import com.expressbank.entity.UserLoginHistoryEntity;
import com.expressbank.enums.ResponseEnum;
import com.expressbank.enums.RoleEnum;
import com.expressbank.exception.CommonException;
import com.expressbank.exception.UserNotFoundException;
import com.expressbank.mapper.SignInMapper;
import com.expressbank.repository.RoleRepository;
import com.expressbank.repository.UserLoginHistoryRepository;
import com.expressbank.repository.UserRepository;
import com.expressbank.security.jwt.JwtTokenUtil;
import com.expressbank.service.AuthenticationService;
import com.expressbank.service.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.expressbank.enums.ResponseEnum.BAD_CREDENTIALS;
import static com.expressbank.enums.ResponseEnum.USER_NOT_FOUND;
import static com.expressbank.util.CommonUtil.generateRefreshToken;

@Service
@Slf4j
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private final EmailValidator emailValidator;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final EmailSender emailSender;

    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    private final AuthenticationManager authManager;
    @Autowired
    private final JwtTokenUtil jwtTokenUtil;
    @Autowired
    private final Environment environment;
    @Autowired
    private final UserLoginHistoryRepository userLoginHistoryRepository;

    @Override
    public ResponseEntity<CommonResponse> signUp(SignUpRequest request) {

        RoleEntity roleEntity =
                roleRepository.findById(RoleEnum.ROLE_USER.ordinal()).orElseThrow(
                        () -> new CommonException(ResponseEnum.ROLE_NOT_FOUND));

        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new CommonException(ResponseEnum.EMAIL_IS_EXIST);
        }

        UserEntity user = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .role(roleEntity)
                .build();
        UserEntity entity = userRepository.save(user);

        String token = confirmationTokenService.saveConfirmationToken(entity);

        String link = "http://localhost:8080/api/auth/v1/confirm?token=" + token;

        System.err.println("link :" + link);

        //  emailSender.sendVerifyAccountLink(request.getEmail(), buildEmail(request.getName(), link));

        return new ResponseEntity<>(CommonResponse.success(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommonResponse> confirmToken(String token) {

        ConfirmationTokenEntity confirmationTokenEntity = confirmationTokenService.getToken(token);

        userRepository.accountVerifiedUser(confirmationTokenEntity.getUserEntity().getEmail());

        return new ResponseEntity<>(CommonResponse.success(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommonResponse> signIn(SignInRequest request, String userAgent) {

        UserEntity user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new CommonException(ResponseEnum.INCORRECT_EMAIL));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new CommonException(ResponseEnum.INCORRECT_PASSWORD);

        if (!user.isAccountVerified())
            throw new CommonException(ResponseEnum.ACCOUNT_DONT_VERIFIED);

        SignInResponse signInResponse = SignInMapper.INSTANCE.entityToDto(user);


        UsernamePasswordAuthenticationToken tokenAuth = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword());
        Authentication authentication;
        try {
            authentication = authManager.authenticate(tokenAuth);
        } catch (Exception ex) {
            if (ex instanceof InternalAuthenticationServiceException) throw new UserNotFoundException(USER_NOT_FOUND);
            else throw new BadCredentialsException(BAD_CREDENTIALS.getStatusMessage());
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String accessToken = jwtTokenUtil.generateToken(userDetails);
        System.err.println("accessToken :"+accessToken);
        final String refreshToken = generateRefreshToken();

        userLoginHistoryRepository.doDeactive(user.getId());

        UserLoginHistoryEntity userLoginHistoryEntity = new UserLoginHistoryEntity();
        userLoginHistoryEntity.setUser(user);
        userLoginHistoryEntity.setRefreshToken(refreshToken);
        userLoginHistoryEntity.setExpiredDate(LocalDateTime.now().plusMonths(1));
        userLoginHistoryRepository.save(userLoginHistoryEntity);

        return new ResponseEntity<>(CommonResponse.success(signInResponse), HttpStatus.OK);
    }


    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
