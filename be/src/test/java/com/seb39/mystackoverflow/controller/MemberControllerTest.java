package com.seb39.mystackoverflow.controller;

import com.seb39.mystackoverflow.entity.Answer;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.mapper.AnswerMapper;
import com.seb39.mystackoverflow.service.AnswerService;
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
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs(uriHost = "api.mystackoverflow.com")
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AnswerService answerService;

//    @Test
//    @DisplayName("특정 사용자의 답변 리스트를 조회한다.")
//    void getAnswersTest() throws Exception {
//        // given
//        Question question1 = new Question();
//        question1.setTitle("Question 01");
//        question1.setId(1L);
//
//        Question question2 = new Question();
//        question2.setTitle("Question 02");
//        question2.setId(2L);
//
//        List<Answer> content = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            Answer answer = new Answer();
//            answer.setContent("answer " + i);
//            if (i % 2 == 0)
//                answer.setQuestion(question1);
//            else
//                answer.setQuestion(question2);
//            content.add(answer);
//        }
//        Page<Answer> result = new PageImpl<>(content);
//
//        BDDMockito.given(answerService.findAnswers(1L, 0))
//                .willReturn(result);
//
//        // expected
//        mockMvc.perform(get("/{memberId}/answers", 1)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(document("answer-list",
//                        pathParameters(
//                                parameterWithName("memberId").description("조회할 답변 리스트를 작성한 사용자의 ID")
//                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("조회된 답변 리스트")
//                                )
//                        )
//                ));
//    }

}