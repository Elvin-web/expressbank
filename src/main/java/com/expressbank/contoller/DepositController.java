package com.expressbank.contoller;

import com.expressbank.dto.CommonResponse;
import com.expressbank.dto.deposit.CreateDepositRequest;
import com.expressbank.dto.deposit.DepositRequest;
import com.expressbank.dto.signIn.request.SignInRequest;
import com.expressbank.service.DepositService;
import com.expressbank.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/deposit")
public class DepositController {

    @Autowired
    private DepositService depositService;

    @PostMapping({"v1/createDeposit"})
    public ResponseEntity<CommonResponse> createDeposit(@Valid @RequestBody CreateDepositRequest request) {
        return depositService.createDeposit(request);
    }

    @PostMapping({"v1/increase"})
    public ResponseEntity<CommonResponse> increaseBalance(@Valid @RequestBody DepositRequest request) {
        return depositService.increaseBalance(request);
    }
}
