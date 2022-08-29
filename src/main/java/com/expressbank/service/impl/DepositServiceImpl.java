package com.expressbank.service.impl;

import com.expressbank.dto.CommonResponse;
import com.expressbank.dto.deposit.CreateDepositRequest;
import com.expressbank.dto.deposit.DepositRequest;
import com.expressbank.entity.DepositEntity;
import com.expressbank.entity.UserEntity;
import com.expressbank.enums.ResponseEnum;
import com.expressbank.exception.CommonException;
import com.expressbank.repository.DepositRespository;
import com.expressbank.repository.UserRepository;
import com.expressbank.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DepositServiceImpl implements DepositService {

    @Autowired
    private DepositRespository respository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<CommonResponse> createDeposit(CreateDepositRequest request) {

        UserEntity userEntity = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CommonException(ResponseEnum.USER_NOT_FOUND));

        DepositEntity depositEntity = DepositEntity.builder()
                .balance(request.getBalance())
                .iban(request.getIban())
                .userEntity(userEntity)
                .createdAt(LocalDateTime.now())
                .build();

        respository.save(depositEntity);
        return new ResponseEntity<>(CommonResponse.success(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommonResponse> increaseBalance(DepositRequest request) {
        if (request.getAmount().equals(0.0))
            throw new CommonException(ResponseEnum.AMOUNT_ZERO);

        DepositEntity depositEntity = respository.findDepositEntityByIban(request.getIban())
                .orElseThrow(() -> new CommonException(ResponseEnum.DEPOSIT_NOT_FOUND));

        if (depositEntity.isAccountBlocked())
            throw new CommonException(ResponseEnum.DEPOSIT_ACCOUNT_BLOCKED);

        depositEntity.setBalance(depositEntity.getBalance() + request.getAmount());
        respository.save(depositEntity);
        return new ResponseEntity<>(CommonResponse.success(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommonResponse> reduceBalance(DepositRequest request) {
        if (request.getAmount().equals(0.0))
            throw new CommonException(ResponseEnum.AMOUNT_ZERO);

        DepositEntity depositEntity = respository.findDepositEntityByIban(request.getIban())
                .orElseThrow(() -> new CommonException(ResponseEnum.DEPOSIT_NOT_FOUND));

        if (depositEntity.isAccountBlocked())
            throw new CommonException(ResponseEnum.DEPOSIT_ACCOUNT_BLOCKED);

        depositEntity.setBalance(depositEntity.getBalance() - request.getAmount());
        respository.save(depositEntity);
        return new ResponseEntity<>(CommonResponse.success(), HttpStatus.OK);
    }

}
