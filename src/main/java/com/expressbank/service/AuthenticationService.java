package com.expressbank.service;

import com.expressbank.dto.CommonResponse;
import com.expressbank.dto.signIn.request.SignInRequest;
import com.expressbank.dto.signUp.request.SignUpRequest;
import org.springframework.http.ResponseEntity;


public interface AuthenticationService {

    ResponseEntity<CommonResponse> signUp(SignUpRequest request);

    ResponseEntity<CommonResponse> confirmToken(String token);

    ResponseEntity<CommonResponse> signIn(SignInRequest request,String userAgent);
}
