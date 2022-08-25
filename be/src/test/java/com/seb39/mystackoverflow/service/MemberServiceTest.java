package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 진행시 비밀번호를 암호화하여 저장한다.")
    void signUpTest(){
        Member member = new Member();
        member.setUsername("abcd1234");
        member.setPassword("12345678");
        member.setEmail("abcd@efgh.com");
        member.setName("SJ");
        member.setRoles("ROLE_USER");
        member.setPhone("010-1234-5678");

        memberService.signUp(member);

        Long memberId = member.getId();
        Member findMember = memberService.findById(memberId);

        Assertions.assertThat(findMember.getUsername()).isEqualTo("abcd1234");
        Assertions.assertThat(findMember.getPassword()).isNotEqualTo("12345678");
    }
}