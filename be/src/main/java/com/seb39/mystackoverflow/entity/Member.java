package com.seb39.mystackoverflow.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String password;

    private String name;

    private String phone;

    private String email;

    private String roles;

    public List<String> getRoleList(){
        if(!StringUtils.hasText(roles))
            return List.of();

        return Arrays.stream(this.roles.split(","))
                .collect(Collectors.toList());
    }

    /**
     * Question 연관관계
     */
    @JsonBackReference(value = "question_id")
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL) //member의 값이 변화하면 다른 값도 모두 변경
    private List<Question> questions = new ArrayList<>();


    @Builder
    public Member(String username, String password, String name, String phone, String email, String roles) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.roles = roles;
    }
}
