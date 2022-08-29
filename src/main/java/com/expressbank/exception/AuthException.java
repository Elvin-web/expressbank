package com.expressbank.exception;

import com.expressbank.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthException extends RuntimeException {

    private final ResponseEnum responseEnum;
}
