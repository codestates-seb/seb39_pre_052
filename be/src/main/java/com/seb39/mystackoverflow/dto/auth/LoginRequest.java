package com.seb39.mystackoverflow.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;
}
