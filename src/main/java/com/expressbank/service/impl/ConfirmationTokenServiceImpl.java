package com.expressbank.service.impl;

import com.expressbank.entity.ConfirmationTokenEntity;
import com.expressbank.entity.UserEntity;
import com.expressbank.enums.ResponseEnum;
import com.expressbank.exception.CommonException;
import com.expressbank.repository.ConfirmationTokenRepository;
import com.expressbank.service.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    @Autowired
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public String saveConfirmationToken(UserEntity userEntity) {

        String token = UUID.randomUUID().toString();

        ConfirmationTokenEntity confirmationToken = ConfirmationTokenEntity.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .userEntity(userEntity)
                .build();

        System.err.println("confirmationToken: " + confirmationToken);

        confirmationTokenRepository.save(confirmationToken);

        return token;
    }

    @Override
    public ConfirmationTokenEntity getToken(String token) {

        ConfirmationTokenEntity confirmationTokenEntity = confirmationTokenRepository.findByToken(token).orElseThrow(
                () -> new CommonException(ResponseEnum.TOKEN_NOT_FOUND));

        if (confirmationTokenEntity.getConfirmedAt() != null) {
            throw new CommonException(ResponseEnum.EMAIL_ALREADY_CONFIRMED);
        }

        if (confirmationTokenEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new CommonException(ResponseEnum.TOKEN_EXPIRED);
        }

        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());

        return confirmationTokenEntity;
    }
}
