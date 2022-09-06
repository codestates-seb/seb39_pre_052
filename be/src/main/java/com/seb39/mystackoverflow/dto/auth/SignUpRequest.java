package com.seb39.mystackoverflow.dto.auth;


import com.seb39.mystackoverflow.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignUpRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank(message = "password는 필수 항목입니다.")
    private String password;

    @NotBlank(message = "name은 필수 항목입니다.")
    private String name;

    public Member toMember() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
    }
}
