package com.expressbank.security;

import com.expressbank.entity.UserEntity;
import com.expressbank.enums.ResponseEnum;
import com.expressbank.exception.UserNotFoundException;
import com.expressbank.repository.UserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = Optional.ofNullable(userRepository.findByUsernameIgnoreCase(username))
                .orElseThrow(() -> new UserNotFoundException(ResponseEnum.USER_NOT_FOUND));
        return new User(userEntity.getUsername(), userEntity.getPassword(),
                AuthorityUtils.createAuthorityList(userEntity.getRole().getName()));
    }
}
