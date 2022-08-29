package com.seb39.mystackoverflow.entity;

import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;
    private String roles;

    public List<String> getRoleList(){
        if(!StringUtils.hasText(roles))
            return List.of();

        return Arrays.stream(this.roles.split(","))
                .collect(Collectors.toList());
    }

    @Builder
    public Member(String email, String password, String name, String roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }
}
