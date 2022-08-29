package com.expressbank.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {

    SUCCESS(1, "Success operation"),
    USER_NOT_FOUND(2, "User not found"),
    EMAIL_IS_EXIST(3, "Email is exist"),
    BAD_REQUEST(4, "The information submitted is incorrect"),
    INCORRECT_EMAIL(5, "Email is incorrect"),
    ROLE_NOT_FOUND(6, "Role not found"),
    TOKEN_NOT_FOUND(7, "Token not found"),
    TOKEN_EXPIRED(8, "Token expired"),
    EMAIL_ALREADY_CONFIRMED(9, "Email Already Confirmed"),
    INCORRECT_PASSWORD(10, "Password is incorrect"),
    ACCOUNT_DONT_VERIFIED(11, "Account is dont verified"),
    DEPOSIT_NOT_FOUND(12, "Deposit not found"),
    DEPOSIT_ACCOUNT_BLOCKED(13, "Deposit account is blocked"),
    AMOUNT_ZERO(14, "Amount is zero"),
    STOCK_NOT_FOUND(15, "Stock not found"),
    ACCESS_DENIED(16, "Access denied"),
    INVALID_HEADER(17, "Invalid header"),
    ACCESS_TOKEN_IS_EXPIRED(18, "Access token is expired"),
    USER_IS_LOGOUT(19, "User has logged out"),
    BAD_CREDENTIALS(20, "Bad credentials"),


    UNKNOWN_ERROR(500, "Unknown error has occured");


    private final Integer statusCode;

    private final String statusMessage;

}
