package com.expressbank.dto.signIn.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse  implements Serializable {

    private Integer id;

    private String name;

    private String email;
}
