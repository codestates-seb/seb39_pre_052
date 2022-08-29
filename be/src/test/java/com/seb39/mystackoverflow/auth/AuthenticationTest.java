package com.seb39.mystackoverflow.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.seb39.mystackoverflow.dto.auth.LoginRequest;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthenticationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    @Autowired
    JwtUtils jwtUtils;

    ObjectMapper om = new ObjectMapper();

    @BeforeEach
    public void initTestUser() {
        Member member = Member.builder()
                .email("abcd@gmail.com")
                .password("1234")
                .name("test")
                .build();
        memberService.signUp(member);
    }

    @Test
    @DisplayName("로그인 시 서버에서 jwt토큰을 발급한다.")
    public void loginJwtTokenTest() throws Exception {
        LoginRequest req = new LoginRequest();
        req.setEmail("abcd@gmail.com");
        req.setPassword("1234");
        String requestJson = om.writeValueAsString(req);
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(header().exists("Authorization"))
                .andDo(print())
                .andReturn();

        String token = result.getResponse().getHeader("Authorization").replace("Bearer ", "");

        String email = jwtUtils.decodeJwtTokenAndGetEmail(token);
        Assertions.assertThat(email).isEqualTo("abcd@gmail.com");
    }

    @Test
    @DisplayName("유효하지 않은 사용자 id로 로그인 시 401로 응답한다")
    public void invalidUsernameLoginTest() throws Exception {
        LoginRequest req = new LoginRequest();
        req.setEmail("invalid@naver.com");
        req.setPassword("1234");
        String requestJson = om.writeValueAsString(req);
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized())
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("유효하지 않은 비밀번호로 로그인 시 401로 응답한다")
    public void invalidPasswordLoginTest() throws Exception {
        LoginRequest req = new LoginRequest();
        req.setEmail("abcd@gmail.com");
        req.setPassword("invalid_password");
        String requestJson = om.writeValueAsString(req);
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized())
                .andDo(print())
                .andReturn();
    }
}
