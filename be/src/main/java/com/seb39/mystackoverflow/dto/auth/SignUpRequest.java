package com.seb39.mystackoverflow.dto.auth;


import com.seb39.mystackoverflow.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank(message = "username은 필수 항목입니다.")
    private String username;

    @NotBlank(message = "password는 필수 항목입니다.")
    private String password;

    @NotBlank(message = "name은 필수 항목입니다.")
    private String name;

    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
            message = "올바른 형식의 휴대폰 번호를 입력해주세요.")
    private String phone;

    @Email
    @NotBlank
    private String email;

    public Member toMember(){
        return Member.builder()
                .username(username)
                .password(password)
                .name(name)
                .phone(phone)
                .email(email)
                .build();
    }
}
