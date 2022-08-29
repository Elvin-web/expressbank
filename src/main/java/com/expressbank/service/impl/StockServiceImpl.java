package com.expressbank.service.impl;

import com.expressbank.dto.CommonResponse;
import com.expressbank.dto.stoks.response.Stock;
import com.expressbank.entity.StockEntity;
import com.expressbank.entity.UserEntity;
import com.expressbank.enums.ResponseEnum;
import com.expressbank.exception.CommonException;
import com.expressbank.mapper.StockMapper;
import com.expressbank.repository.StockRepository;
import com.expressbank.service.StockService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;


    @Override
    public ResponseEntity<CommonResponse> getStockList() {

        try (FileReader reader = new FileReader("C:\\Users\\DELL\\Desktop\\expressbank\\src\\main\\resources\\json-data\\stocks.json")) {

            ObjectMapper objectMapper = new ObjectMapper();
            List<Stock> stockDataList = objectMapper.readValue(reader, new TypeReference<>() {
            });

            List<StockEntity> stockEntityList = new LinkedList<>();

            for (Stock stock : stockDataList) {
                stockEntityList.add(StockMapper.INSTANCE.dtoToEntity(stock));
            }
            stockRepository.saveAll(stockEntityList);

            return new ResponseEntity<>(CommonResponse.success(stockDataList), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommonException(ResponseEnum.UNKNOWN_ERROR);
        }
    }

    @Override
    public StockEntity findStockById(Integer id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new CommonException(ResponseEnum.STOCK_NOT_FOUND));
    }


}
