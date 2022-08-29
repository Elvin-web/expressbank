package com.expressbank.service;

import com.expressbank.dto.CommonResponse;
import com.expressbank.entity.StockEntity;
import org.springframework.http.ResponseEntity;

public interface StockService {
    ResponseEntity<CommonResponse> getStockList();

    StockEntity findStockById(Integer id);
}
