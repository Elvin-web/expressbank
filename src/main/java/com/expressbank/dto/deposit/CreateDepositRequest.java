package com.expressbank.dto.deposit;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CreateDepositRequest implements Serializable {

    private Double balance;

    private String iban;

    private Integer userId;
}
