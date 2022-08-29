package com.expressbank.dto.signIn.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
public class SignInRequest implements Serializable {
    @NotBlank(message = "Email is mandatory!")
    private String email;
    @NotBlank(message = "Password is mandatory!")
    private String password;

    @NotBlank(message = "Username is mandatory")
    private String username;
}
