package com.seb39.mystackoverflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seb39.mystackoverflow.auth.PrincipalDetails;
import com.seb39.mystackoverflow.dto.AnswerDto;
import com.seb39.mystackoverflow.entity.Answer;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.mapper.AnswerMapper;
import com.seb39.mystackoverflow.repository.MemberRepository;
import com.seb39.mystackoverflow.service.AnswerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.seb39.mystackoverflow.utils.ApiDocumentUtils.getRequestPreprocessor;
import static com.seb39.mystackoverflow.utils.ApiDocumentUtils.getResponsePreprocessor;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs(uriHost = "api.mystackoverflow.com")
@WithUserDetails(value = "abcd@naver.com", userDetailsServiceBeanName = "principalDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    @MockBean
    private AnswerMapper answerMapper;

    @MockBean
    private PrincipalDetails principal;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        Member member = Member.builder()
                .email("abcd@naver.com")
                .roles("ROLE_USER")
                .build();
        memberRepository.save(member);
    }

    @AfterEach
    void afterEach() {
        memberRepository.deleteAll();
    }

    @Test
    void postAnswerTest() throws Exception {
        // given
        AnswerDto.Post requestDto = new AnswerDto.Post();
        requestDto.setContent("Create Answer");

        BDDMockito.given(answerMapper.answerPostToAnswer(any()))
                .willReturn(new Answer());

        BDDMockito.given(answerService.createAnswer(any(), any(), any()))
                .willReturn(1L);

        String requestJson = objectMapper.writeValueAsString(requestDto);

        // expected
        mockMvc.perform(post("/api/answers")
                        .param("question-id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andDo(document("answer-create",
                        getRequestPreprocessor(),
                        getResponsePreprocessor(),
                        requestParameters(
                                parameterWithName("question-id").description("작성한 답변의 질문 ID")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("답변 내용")
                                )
                        )
                ));
    }

    @Test
    void patchAnswerTest() throws Exception {
        // given
        AnswerDto.Patch requestDto = new AnswerDto.Patch();
        requestDto.setContent("updated Answer");

        BDDMockito.given(answerMapper.answerPatchToAnswer(any(), any()))
                .willReturn(new Answer());

        BDDMockito.doNothing().when(answerService).updateAnswer(any(), any());

        String requestJson = objectMapper.writeValueAsString(requestDto);

        // expected
        mockMvc.perform(patch("/api/answers/{answerId}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andDo(document("answer-update",
                        getRequestPreprocessor(),
                        getResponsePreprocessor(),
                        pathParameters(
                                parameterWithName("answerId").description("수정할 답변의 ID")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("답변 내용")
                                )
                        )
                ));
    }

    @Test
    void deleteAnswerTest() throws Exception {
        // given
        BDDMockito.doNothing().when(answerService).deleteAnswer(any(), any());

        // expected
        mockMvc.perform(delete("/api/answers/{answerId}",1))
                .andExpect(status().isOk())
                .andDo(document("answer-delete",
                        getRequestPreprocessor(),
                        getResponsePreprocessor(),
                        pathParameters(
                                parameterWithName("answerId").description("수정할 답변의 ID")
                        )
                ));
    }
}