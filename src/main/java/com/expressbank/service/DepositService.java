package com.expressbank.service;

import com.expressbank.dto.CommonResponse;
import com.expressbank.dto.deposit.CreateDepositRequest;
import com.expressbank.dto.deposit.DepositRequest;
import org.springframework.http.ResponseEntity;

public interface DepositService {

    ResponseEntity<CommonResponse> createDeposit(CreateDepositRequest request);

    ResponseEntity<CommonResponse> increaseBalance(DepositRequest request);

    ResponseEntity<CommonResponse> reduceBalance(DepositRequest request);
}
