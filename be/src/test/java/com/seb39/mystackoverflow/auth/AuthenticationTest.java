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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.seb39.mystackoverflow.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.seb39.mystackoverflow.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriHost = "api.mystackoverflow.com")
class AuthenticationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    @Autowired
    JwtManager jwtManager;

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
    @DisplayName("????????? ??? ???????????? jwt????????? ????????????.")
    public void loginJwtTokenTest() throws Exception {
        // given
        LoginRequest req = new LoginRequest();
        req.setEmail("abcd@gmail.com");
        req.setPassword("1234");
        String requestJson = om.writeValueAsString(req);

        // expected
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(header().exists("Authorization"))
                .andDo(document("login-success",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestFields(
                                        List.of(
                                                fieldWithPath("email").type(JsonFieldType.STRING).description("????????? ?????????"),
                                                fieldWithPath("password").type(JsonFieldType.STRING).description("????????????")
                                        )
                                ),
                                responseHeaders(
                                        headerWithName("Authorization").description("JWT ?????? ????????? ?????? ??????")
                                )
                        )
                )
                .andReturn();

        String token = result.getResponse().getHeader("Authorization").replace("Bearer ", "");
        String email = jwtManager.decodeJwtTokenAndGetEmail(token);
        Assertions.assertThat(email).isEqualTo("abcd@gmail.com");
    }

    @Test
    @DisplayName("???????????? ?????? ????????? email??? ????????? ??? 401??? ????????????")
    public void invalidUsernameLoginTest() throws Exception {
        LoginRequest req = new LoginRequest();
        req.setEmail("invalid@naver.com");
        req.setPassword("1234");
        String requestJson = om.writeValueAsString(req);
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized())
                .andDo(document("login-fail",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("????????? ?????????"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("????????????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("?????? ??????"),
                                        fieldWithPath("failureReason").type(JsonFieldType.STRING).description("????????? ?????? ??????.")
                                )
                        )
                ))
                .andReturn();
    }

    @Test
    @DisplayName("???????????? ?????? ??????????????? ????????? ??? 401??? ????????????")
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
