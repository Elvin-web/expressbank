package com.expressbank.dto.order.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SellOrderRequest implements Serializable {

    private Integer userId;

    private Integer stockId;

    private Double quantity;
}
