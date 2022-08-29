package com.expressbank.contoller;

import com.expressbank.dto.CommonResponse;
import com.expressbank.dto.signIn.request.SignInRequest;
import com.expressbank.dto.signUp.request.SignUpRequest;
import com.expressbank.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private  AuthenticationService service;

    @GetMapping("/home")
    public String home() {
        return "Welcome home page !!";
    }

    @PostMapping("v1/sign-up")
    public ResponseEntity<CommonResponse> register(@Valid @RequestBody SignUpRequest request){
        return service.signUp(request);
    }

    @GetMapping(path = "v1/confirm")
    public ResponseEntity<CommonResponse> confirm(@RequestParam("token") String token) {
        return service.confirmToken(token);
    }

    @PostMapping({"v1/sign-in"})
    public ResponseEntity<CommonResponse> login(@Valid @RequestBody SignInRequest request
    , @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        return service.signIn(request,userAgent);
    }
}
