package com.expressbank.service;

import com.expressbank.entity.ConfirmationTokenEntity;
import com.expressbank.entity.UserEntity;

public interface ConfirmationTokenService {

    String saveConfirmationToken(UserEntity userEntity);

    ConfirmationTokenEntity getToken(String token);
}
