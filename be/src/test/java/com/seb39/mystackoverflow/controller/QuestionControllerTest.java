package com.seb39.mystackoverflow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seb39.mystackoverflow.dto.QuestionDto;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.mapper.QuestionMapper;
import com.seb39.mystackoverflow.repository.MemberRepository;
import com.seb39.mystackoverflow.service.QuestionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static com.seb39.mystackoverflow.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.seb39.mystackoverflow.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs(uriHost = "api.mystackoverflow.com")
@WithUserDetails(value = "test@email.com", userDetailsServiceBeanName = "principalDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private QuestionMapper questionMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void 데이터_준비() {
        Member member = Member.builder()
                .email("test@email.com")
                .roles("ROLE_USER")
                .build();
        memberRepository.save(member);
    }

    @AfterEach
    void afterEach() {
        memberRepository.deleteAll();
    }

    @Test
    public void 질문등록_테스트() throws Exception {
        //given
        QuestionDto.Post requestBody = new QuestionDto.Post();
        requestBody.setContent("Test content");
        requestBody.setTitle("Test title");

        QuestionDto.Response response = new QuestionDto.Response(1L,
                "Test title",
                "Test content",
                0,
                0,
                LocalDateTime.now(),
                LocalDateTime.now(),
                memberRepository.findByEmail("test@email.com").get());

        given(questionMapper.questionPostToQuestion(any())).willReturn(new Question());
        given(questionService.createQuestion(any(), any())).willReturn(new Question());
        given(questionMapper.questionToQuestionResponse(Mockito.any(Question.class))).willReturn(response);
        String requestToJson = objectMapper.writeValueAsString(requestBody);

        //when
        ResultActions actions = mockMvc.perform(post("/api/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestToJson));
        //then
        actions.andExpect(status().isCreated())
                .andDo(document("question-create",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("질문 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("질문 내용")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("질문 제목"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("질문 내용"),
                                        fieldWithPath("data.view").type(JsonFieldType.NUMBER).description("질문 조회수"),
                                        fieldWithPath("data.vote").type(JsonFieldType.NUMBER).description("질문 추천수"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("질문 생성 일시"),
                                        fieldWithPath("data.lastModifiedAt").type(JsonFieldType.STRING).description("질문 수정 일시"),
                                        fieldWithPath("data.member").type(JsonFieldType.NUMBER).description("작성자 식별자")
                                )
                        )
                ));
    }

    @Test
    public void 질문수정_테스트() throws Exception{
        //given
        QuestionDto.Post requestBody = new QuestionDto.Post();
        requestBody.setContent("Test content");
        requestBody.setTitle("Test title");
        String requestToJson = objectMapper.writeValueAsString(requestBody);

        long questionId = 1L;
        QuestionDto.Patch patch = new QuestionDto.Patch();
        patch.setId(questionId);
        patch.setContent("Update Content");
        patch.setTitle("Update Title");

        QuestionDto.Response response = new QuestionDto.Response(1L,
                patch.getTitle(),
                patch.getContent(),
                0,
                0,
                LocalDateTime.now(),
                LocalDateTime.now(),
                memberRepository.findByEmail("test@email.com").get()
                );
        given(questionMapper.questionPatchToQuestion(Mockito.any(QuestionDto.Patch.class))).willReturn(new Question());
        given(questionService.updateQuestion(Mockito.any(Question.class), any())).willReturn(new Question());
        given(questionMapper.questionToQuestionResponse(Mockito.any(Question.class))).willReturn(response);
        //when
        ResultActions actions = mockMvc.perform(
                patch("/api/questions/{id}", questionId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestToJson)
        );

        //then
        actions.andExpect(status().isOk())
                .andDo(document("question-update",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("id").description("질문 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 질문 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 질문 내용")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("질문 제목"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("질문 내용"),
                                        fieldWithPath("data.view").type(JsonFieldType.NUMBER).description("질문 조회수"),
                                        fieldWithPath("data.vote").type(JsonFieldType.NUMBER).description("질문 추천수"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("질문 생성 일시"),
                                        fieldWithPath("data.lastModifiedAt").type(JsonFieldType.STRING).description("질문 수정 일시"),
                                        fieldWithPath("data.member").type(JsonFieldType.NUMBER).description("작성자 식별자")
                                )
                        )
                ));
    }

    @Test
    public void 질문목록조회_테스트() throws Exception {
        //given
        Member member = memberRepository.findByEmail("test@email.com").get();

        Question question1 = getQuestion(member);
        Question question2 = getQuestion2(member);

        Page<Question> questions = new PageImpl<>(List.of(question1, question2),
                PageRequest.of(0, 10, Sort.by("id").descending()), 2);

        List<QuestionDto.Response> responses = List.of(new QuestionDto.Response(1L, "title", "content", 0, 0, LocalDateTime.now(), LocalDateTime.now(), member),
                new QuestionDto.Response(2L, "title", "content", 0, 0, LocalDateTime.now(), LocalDateTime.now(), member));


        given(questionService.findQuestions(Mockito.anyInt(), Mockito.anyInt())).willReturn(questions);
        given(questionMapper.questionsToQuestionResponses(Mockito.anyList())).willReturn(responses);

        //when
        ResultActions actions = mockMvc.perform(get("/api/questions").param("page", "1").param("size", "2").accept(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andDo(document("questions-search",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                List.of(
                                        parameterWithName("page").description("Page 번호"),
                                        parameterWithName("size").description("Page Size")
                                )
                        ),
                        responseFields(
                                fieldWithPath("data.[]").type(JsonFieldType.ARRAY).description("질문 리스트"),
                                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("질문 제목"),
                                fieldWithPath("data.[].content").type(JsonFieldType.STRING).description("질문 내용"),
                                fieldWithPath("data.[].view").type(JsonFieldType.NUMBER).description("질문 조회수"),
                                fieldWithPath("data.[].vote").type(JsonFieldType.NUMBER).description("질문 추천수"),
                                fieldWithPath("data.[].createdAt").type(JsonFieldType.STRING).description("질문 생성일"),
                                fieldWithPath("data.[].lastModifiedAt").type(JsonFieldType.STRING).description("질문 수정일"),
                                fieldWithPath("data.[].member").type(JsonFieldType.NUMBER).description("작성자 식별자"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("현재 사이즈"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("질문 전체 수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 개수")
                        )
                ));
    }

    private Question getQuestion2(Member member) {
        Question question2 = new Question();
        question2.setId(2L);
        question2.setVote(0);
        question2.setView(0);
        question2.setContent("test content2");
        question2.setTitle("test title2");
        question2.setCreatedAt(LocalDateTime.now());
        question2.setLastModifiedAt(LocalDateTime.now());
        question2.setMember(member);
        return question2;
    }

    private Question getQuestion(Member member) {
        Question question1 = new Question();
        question1.setId(1L);
        question1.setVote(0);
        question1.setView(0);
        question1.setContent("test content");
        question1.setTitle("test title");
        question1.setCreatedAt(LocalDateTime.now());
        question1.setLastModifiedAt(LocalDateTime.now());
        question1.setMember(member);
        return question1;
    }

    @Test
    public void 질문삭제_테스트() throws Exception {
        //given
        long questionId = 1L;
        long memberId = 1L;
        //when
        doNothing().when(questionService).delete(questionId, memberId);
        ResultActions actions = mockMvc.perform(delete("/api/questions/{id}", questionId));
        //then
        actions.andExpect(status().isNoContent())
                .andDo(document("question-delete",
                        pathParameters(
                                parameterWithName("id").description("삭제할 질문 식별자")
                        )
                        ));
    }

}