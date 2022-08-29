package com.expressbank.dto.deposit;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
public class DepositRequest implements Serializable {

    private Double amount;
    @NotBlank(message = "Iban is mandatory!")
    private String iban;
}
