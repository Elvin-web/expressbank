package com.expressbank.dto;

import com.expressbank.enums.ResponseEnum;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

import static com.expressbank.enums.ResponseEnum.SUCCESS;

@Data
@Builder
@Slf4j
public class CommonResponse implements Serializable {

    private Integer statusCode;

    private String statusMessage;

    private Object data;

    public static CommonResponse success(){
        log.info("Success operation!");
        return CommonResponse.builder()
                .statusCode(SUCCESS.getStatusCode())
                .statusMessage(SUCCESS.getStatusMessage())
                .data(null)
                .build();
    }

    public static CommonResponse success(Object data){
        log.info("Success operation!");
        log.info("response = " + data);
        return CommonResponse.builder()
                .statusCode(SUCCESS.getStatusCode())
                .statusMessage(SUCCESS.getStatusMessage())
                .data(data)
                .build();
    }

    public static CommonResponse error(ResponseEnum responseEnum){
        return CommonResponse.builder()
                .statusCode(responseEnum.getStatusCode())
                .statusMessage(responseEnum.getStatusMessage())
                .data(null)
                .build();
    }
}
