package com.expressbank.contoller;

import com.expressbank.dto.CommonResponse;
import com.expressbank.dto.order.request.BuyOrderRequest;
import com.expressbank.dto.order.request.SellOrderRequest;
import com.expressbank.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping({"v1/buy"})
    public ResponseEntity<CommonResponse> createDeposit(@Valid @RequestBody BuyOrderRequest request) {
        return service.buyOrder(request);
    }

    @PostMapping({"v1/sell"})
    public ResponseEntity<CommonResponse> createDeposit(@Valid @RequestBody SellOrderRequest request) {
        return service.sellOrder(request);
    }
}
