package com.expressbank.mapper;

import com.expressbank.dto.signIn.response.SignInResponse;
import com.expressbank.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class SignInMapper {

    public static final SignInMapper INSTANCE = Mappers.getMapper(SignInMapper.class);

    public abstract SignInResponse entityToDto(UserEntity userEntity);
}
