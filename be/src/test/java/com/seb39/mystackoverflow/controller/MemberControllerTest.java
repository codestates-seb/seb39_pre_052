package com.seb39.mystackoverflow.controller;

import com.seb39.mystackoverflow.dto.AnswerDto;
import com.seb39.mystackoverflow.dto.QuestionDto;
import com.seb39.mystackoverflow.entity.Answer;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.mapper.AnswerMapper;
import com.seb39.mystackoverflow.mapper.MemberMapper;
import com.seb39.mystackoverflow.mapper.QuestionMapper;
import com.seb39.mystackoverflow.service.AnswerService;
import com.seb39.mystackoverflow.service.MemberService;
import com.seb39.mystackoverflow.service.QuestionService;
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

import static com.seb39.mystackoverflow.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.seb39.mystackoverflow.util.ApiDocumentUtils.getResponsePreProcessor;
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
    QuestionService questionService;

    @MockBean
    QuestionMapper questionMapper;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper memberMapper;


    @MockBean
    AnswerService answerService;

    @MockBean
    AnswerMapper answerMapper;

    @Test
    @DisplayName("?????? ???????????? ?????? ???????????? ????????????.")
    void getQuestionsTest() throws Exception {
        // given
        Long questionId = 3L;
        int page = 1;
        given(questionService.findQuestions(anyLong(), anyInt()))
                .willReturn(new PageImpl<>(List.of(
                        new Question(),
                        new Question(),
                        new Question())));

        Long memberId = 1L;
        Member member = new Member();
        member.setId(memberId);
        member.setName("nameA");
        LocalDateTime createdAt = LocalDateTime.now();
        QuestionDto.Response.MemberSimple memberSimple = new QuestionDto.Response.MemberSimple(member.getId(), member.getName());

        List<QuestionDto.Response> data = List.of(
                new QuestionDto.Response(1L, "title01", "content01", 1111, 11, createdAt, createdAt, memberSimple, 0),
                new QuestionDto.Response(2L, "title02", "content02", 768, 22, createdAt, createdAt, memberSimple, 0),
                new QuestionDto.Response(3L, "title03", "content03", 353, -7, createdAt, createdAt, memberSimple, 0)
        );
        given(questionMapper.questionsToQuestionResponses(any()))
                .willReturn(data);

        // expected
        mockMvc.perform(get("/api/members/{id}/questions", memberId)
                        .queryParam("page", "1"))
                .andExpect(jsonPath("data.length()").value(3))
                .andExpect(jsonPath("data[0].id").value(1L))
                .andExpect(jsonPath("data[0].title").value("title01"))
                .andExpect(jsonPath("data[0].content").value("content01"))
                .andExpect(jsonPath("data[0].view").value(1111))
                .andExpect(jsonPath("data[0].vote").value(11))
                .andExpect(jsonPath("pageInfo.page").value(1))
                .andExpect(jsonPath("pageInfo.size").value(3))
                .andExpect(jsonPath("pageInfo.totalElements").value(3))
                .andExpect(jsonPath("pageInfo.totalPages").value(1))
                .andDo(document("member-questions",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("id").description("????????? ????????? ????????? ID")
                        ),
                        requestParameters(
                                parameterWithName("page").description("?????? ????????? ??????. ???????????? 1?????????").optional()
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("????????? ?????? ?????????"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("?????? ID"),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data[].content").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data[].view").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("data[].vote").type(JsonFieldType.NUMBER).description("?????? ?????? ???"),
                                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("data[].lastModifiedAt").type(JsonFieldType.STRING).description("?????? ????????? ?????? ??????"),
                                fieldWithPath("data[].member").type(JsonFieldType.OBJECT).description("?????? ????????? ??????"),
                                fieldWithPath("data.[].member.memberId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                                fieldWithPath("data.[].member.memberName").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                                fieldWithPath("data.[].answerNum").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("?????? ?????? ???"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("??? ????????? ??????")
                        )
                ));
    }


    @Test
    @DisplayName("?????? ???????????? ?????? ???????????? ????????????.")
    void getAnswersTest() throws Exception {
        // given
        Long questionId = 3L;
        int page = 1;
        given(answerService.findAnswers(anyLong(), anyInt()))
                .willReturn(new PageImpl<>(List.of(
                        new Answer(),
                        new Answer(),
                        new Answer())));

        Long memberId = 1L;
        Member member = new Member();
        member.setId(memberId);

        List<AnswerDto.Response> data = List.of(
                new AnswerDto.Response(1L, 11, false, LocalDateTime.now(), 1L, "question 01"),
                new AnswerDto.Response(2L, 23, true, LocalDateTime.now(), 2L, "question 02"),
                new AnswerDto.Response(3L, -33, false, LocalDateTime.now(), 1L, "question 01")
        );

        given(answerMapper.answersToAnswerResponses(any()))
                .willReturn(data);

        // expected
        mockMvc.perform(get("/api/members/{id}/answers", memberId)
                        .queryParam("page", "1"))
                .andExpect(jsonPath("data.length()").value(3))
                .andExpect(jsonPath("data[0].id").value(1L))
                .andExpect(jsonPath("data[0].vote").value(11))
                .andExpect(jsonPath("data[0].accepted").value(false))
                .andExpect(jsonPath("data[0].accepted").value(false))
                .andExpect(jsonPath("data[0].question.id").value(1L))
                .andExpect(jsonPath("data[0].question.title").value("question 01"))
                .andExpect(jsonPath("pageInfo.page").value(1))
                .andExpect(jsonPath("pageInfo.size").value(3))
                .andExpect(jsonPath("pageInfo.totalElements").value(3))
                .andExpect(jsonPath("pageInfo.totalPages").value(1))
                .andDo(document("member-answers",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("id").description("????????? ????????? ????????? ID")
                        ),
                        requestParameters(
                                parameterWithName("page").description("?????? ????????? ??????. ???????????? 1?????????").optional()
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("????????? ?????? ?????????"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("?????? ID"),
                                fieldWithPath("data[].vote").type(JsonFieldType.NUMBER).description("?????? ?????? ???"),
                                fieldWithPath("data[].accepted").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                                fieldWithPath("data[].answeredTime").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("data[].question.id").type(JsonFieldType.NUMBER).description("????????? ?????? ????????? ID"),
                                fieldWithPath("data[].question.title").type(JsonFieldType.STRING).description("????????? ?????? ????????? ??????"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("?????? ?????? ???"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("??? ????????? ??????")
                        )
                ));
    }
}