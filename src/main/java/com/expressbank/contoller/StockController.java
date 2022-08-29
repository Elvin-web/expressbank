package com.expressbank.contoller;

import com.expressbank.dto.CommonResponse;
import com.expressbank.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService service;

    @GetMapping
    public ResponseEntity<CommonResponse> getStockData() {
        return service.getStockList();
    }
}
