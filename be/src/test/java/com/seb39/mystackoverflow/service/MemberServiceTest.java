package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void beforeEach() {
        Member member = Member.builder()
                .email("abcd@gmail.com")
                .password("12345678")
                .name("SJ")
                .roles("ROLE_USER")
                .build();

        memberService.signUp(member);
    }

    @Test
    @DisplayName("회원가입 진행시 비밀번호를 암호화하여 저장한다.")
    void signUpTest() {
        String email = "abcd@gmail.com";
        Member findMember = memberService.findByEmail(email);
        assertThat(findMember.getEmail()).isEqualTo(email);
        assertThat(findMember.getPassword()).isNotEqualTo("12345678");
    }

    @Test
    @DisplayName("email 값은 유일해야 한다.")
    void existUsername() {
        Member newMember = Member.builder()
                .email("abcd@gmail.com")
                .password("12345678")
                .name("SJ")
                .roles("ROLE_USER")
                .build();
        assertThatThrownBy(() -> memberService.signUp(newMember))
                .isInstanceOf(IllegalArgumentException.class);
    }
}