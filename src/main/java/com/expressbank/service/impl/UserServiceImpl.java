package com.expressbank.service.impl;

import com.expressbank.entity.UserEntity;
import com.expressbank.enums.ResponseEnum;
import com.expressbank.exception.CommonException;
import com.expressbank.repository.UserRepository;
import com.expressbank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<UserEntity> findUserByEmail(String email) {

        return userRepository.findUserByEmail(email);
    }

    @Override
    public UserEntity findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CommonException(ResponseEnum.USER_NOT_FOUND));
    }

}
