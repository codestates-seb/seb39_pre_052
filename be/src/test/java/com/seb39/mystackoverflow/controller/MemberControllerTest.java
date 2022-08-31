package com.seb39.mystackoverflow.controller;

import com.seb39.mystackoverflow.dto.AnswerDto;
import com.seb39.mystackoverflow.dto.MultiResponseDto;
import com.seb39.mystackoverflow.entity.Answer;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.mapper.AnswerMapper;
import com.seb39.mystackoverflow.repository.AnswerRepository;
import com.seb39.mystackoverflow.service.AnswerService;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.seb39.mystackoverflow.utils.ApiDocumentUtils.getRequestPreprocessor;
import static com.seb39.mystackoverflow.utils.ApiDocumentUtils.getResponsePreprocessor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs(uriHost = "api.mystackoverflow.com")
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AnswerService answerService;

    @MockBean
    AnswerMapper answerMapper;

    @Test
    @DisplayName("특정 사용자의 답변 리스트를 조회한다.")
    void getAnswersTest() throws Exception {
        // given
        List<Answer> content = List.of(
                new Answer(), new Answer(), new Answer(), new Answer()
        );
        Page<Answer> result = new PageImpl<>(content);

        given(answerService.findAnswers(1L, 0))
                .willReturn(result);

        AnswerDto.Response response = AnswerDto.Response.builder()
                .questionId(2L)
                .questionTitle("Question 01")
                .votes(10)
                .accepted(false)
                .answeredTime(LocalDateTime.now())
                .build();
        given(answerMapper.answerToAnswerResponse(any()))
                .willReturn(response);

        // expected
        mockMvc.perform(get("/api/members/{memberId}/answers", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page", "1")
                )
                .andExpect(status().isOk())
                .andDo(document("answer-list",
                        getRequestPreprocessor(),
                        getResponsePreprocessor(),
                        requestParameters(
                                parameterWithName("page").optional().description("요청할 페이지의 수. 기본값 = 1")
                        ),
                        pathParameters(
                                parameterWithName("memberId").description("조회할 답변 리스트를 작성한 사용자의 ID")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("조회된 답변 리스트"),
                                        fieldWithPath("data[].questionId").type(JsonFieldType.NUMBER).description("조회된 답변의 질문 ID"),
                                        fieldWithPath("data[].questionTitle").type(JsonFieldType.STRING).description("조회된 답변의 질문 제목"),
                                        fieldWithPath("data[].votes").type(JsonFieldType.NUMBER).description("조회된 답변의 추천 수"),
                                        fieldWithPath("data[].accepted").type(JsonFieldType.BOOLEAN).description("조회된 답변의 채택 유무"),
                                        fieldWithPath("data[].answeredTime").type(JsonFieldType.STRING).description("조회된 답변의 질문 ID"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("요청한 페이지 번호"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("요청한 페이지의 데이터 수량"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("요청한 member의 총 답변 수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                                )
                        )
                ));
    }

}