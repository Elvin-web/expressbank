package com.expressbank.handler;

import com.expressbank.dto.CommonResponse;
import com.expressbank.enums.ErrorLevel;
import com.expressbank.exception.CommonException;
import com.expressbank.model.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.expressbank.enums.ResponseEnum.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(CommonException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse commonException(CommonException commonException) {
        log.error(commonException.getResponseEnum().getStatusMessage());
        CommonResponse response = CommonResponse.builder()
                .statusCode(commonException.getResponseEnum().getStatusCode())
                .statusMessage(commonException.getResponseEnum().getStatusMessage())
                .data(null)
                .build();
        log.error("response = " + response);
        return response;
    }


//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public CommonResponse constraintViolationException(ConstraintViolationException commonException){
//       // log.error(commonException.getResponseEnum().getStatusMessage());
//        return CommonResponse.builder()
////                .statusCode(commonException.getResponseEnum().getStatusCode())
////                .statusMessage(commonException.getResponseEnum().getStatusMessage())
//                .statusCode(commonException.getI)
//                .statusMessage(commonException.getMessage())
//                .data(null)
//                .build();
//    }




    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.error(String.format("Validated error: {}", ex));

        List<ValidationError> checks = new LinkedList<>();
        BindingResult bindingResult = ex.getBindingResult();
        checks.addAll(
                bindingResult
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> new ValidationError(
                                ErrorLevel.ERROR,
                                fieldError.getField(),
                                fieldError.getDefaultMessage()
                        ))
                        .collect(Collectors.toList()));
        checks.addAll(
                bindingResult
                        .getGlobalErrors()
                        .stream()
                        .map(globalError -> new ValidationError(
                                ErrorLevel.ERROR,
                                globalError.getObjectName(),
                                globalError.getDefaultMessage()
                        ))
                        .collect(Collectors.toList())
        );

        ResponseEntity responseEntity = new ResponseEntity<>(
                CommonResponse.builder()
                        .statusCode(BAD_REQUEST.getStatusCode())
                        .statusMessage(BAD_REQUEST.getStatusMessage())
                        .data(checks)
                        .build(),
                headers,
                HttpStatus.BAD_REQUEST);
        log.error("response = " + responseEntity.toString());
        return responseEntity;
    }
}
