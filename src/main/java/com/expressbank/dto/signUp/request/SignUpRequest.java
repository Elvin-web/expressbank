package com.expressbank.dto.signUp.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
public class SignUpRequest implements Serializable {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Please enter a valid email!")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6)
    @Pattern(regexp = "^[a-zA-Z0-9]{6}", message = "You can use a minimum of 6 symbols!")
    private String password;


    @NotBlank(message = "Username is mandatory")
    private String username;
}
