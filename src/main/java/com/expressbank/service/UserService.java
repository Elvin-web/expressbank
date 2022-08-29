package com.expressbank.service;

import com.expressbank.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> findUserByEmail(String email);

    UserEntity findUserById(Integer id);
}
