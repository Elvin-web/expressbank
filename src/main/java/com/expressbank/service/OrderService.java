package com.expressbank.service;

import com.expressbank.dto.CommonResponse;
import com.expressbank.dto.order.request.BuyOrderRequest;
import com.expressbank.dto.order.request.SellOrderRequest;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity<CommonResponse> buyOrder(BuyOrderRequest request);

    ResponseEntity<CommonResponse> sellOrder(SellOrderRequest request);

}
