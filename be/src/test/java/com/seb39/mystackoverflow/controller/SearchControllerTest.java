package com.seb39.mystackoverflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seb39.mystackoverflow.dto.QuestionDto;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.mapper.QuestionDetailMapper;
import com.seb39.mystackoverflow.mapper.QuestionMapper;
import com.seb39.mystackoverflow.repository.MemberRepository;
import com.seb39.mystackoverflow.service.QuestionDetailService;
import com.seb39.mystackoverflow.service.QuestionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs(uriHost = "api.mystackoverflow.com")
@WithUserDetails(value = "test@email.com", userDetailsServiceBeanName = "principalDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private QuestionMapper questionMapper;

    @MockBean
    private QuestionDetailService questionDetailService;

    @MockBean
    private QuestionDetailMapper questionDetailMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void 데이터_준비() {
        Member member = Member.builder()
                .email("test@email.com")
                .name("minwoo")
                .roles("ROLE_USER")
                .build();

        Member member1 = Member.builder()
                .email("testA@email.com")
                .name("yongju")
                .roles("ROLE_USER")
                .build();

        memberRepository.save(member);
        memberRepository.save(member1);
    }

    @AfterEach
    void afterEach() {
        memberRepository.deleteAll();
    }

    @Test
    public void 질문제목검색_테스트() throws Exception {
        //given
        Member member1 = memberRepository.findByEmail("test@email.com").get();
        Member member2 = memberRepository.findByEmail("testA@email.com").get();
        QuestionDto.Response.MemberSimple memberSimple1 = new QuestionDto.Response.MemberSimple(member1.getId(), member1.getName());
        QuestionDto.Response.MemberSimple memberSimple2 = new QuestionDto.Response.MemberSimple(member2.getId(), member2.getName());

        Question question1 = getQuestion(member1);
        Question question2 = getQuestion2(member2);

        Page<Question> questions = new PageImpl<>(List.of(question1, question2),
                PageRequest.of(0, 10, Sort.by("id").descending()), 2);

        List<QuestionDto.Response> responses = List.of(new QuestionDto.Response(1L, "Spring Boot", "content", 0, 0, LocalDateTime.now(), LocalDateTime.now(), memberSimple1),
                new QuestionDto.Response(2L, "Java Spring Boot", "content", 0, 0, LocalDateTime.now(), LocalDateTime.now(), memberSimple2));

        given(questionService.findQuestionsByTitle(Mockito.anyString(), Mockito.anyInt())).willReturn(questions);
        given(questionMapper.questionsToQuestionResponses(Mockito.anyList())).willReturn(responses);

        //when
        ResultActions actions = mockMvc.perform(get("/api/search").param("keyword", "spring").param("page", "1").param("size", "2").accept(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andDo(document("questions-searchByTitle",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                List.of(
                                        parameterWithName("keyword").description("검색 키워드"),
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
                                fieldWithPath("data.[].member").type(JsonFieldType.OBJECT).description("작성자 데이터"),
                                fieldWithPath("data.[].member.memberId").type(JsonFieldType.NUMBER).description("작성자 식별자"),
                                fieldWithPath("data.[].member.memberName").type(JsonFieldType.STRING).description("작성자 이름"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("현재 사이즈"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("질문 전체 수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 개수")
                        )));
    }

    @Test
    public void 질문내용검색_테스트() throws Exception {
        //given
        Member member1 = memberRepository.findByEmail("test@email.com").get();
        Member member2 = memberRepository.findByEmail("testA@email.com").get();
        QuestionDto.Response.MemberSimple memberSimple1 = new QuestionDto.Response.MemberSimple(member1.getId(), member1.getName());
        QuestionDto.Response.MemberSimple memberSimple2 = new QuestionDto.Response.MemberSimple(member2.getId(), member2.getName());

        Question question1 = getQuestion(member1);
        Question question2 = getQuestion2(member2);

        Page<Question> questions = new PageImpl<>(List.of(question1, question2),
                PageRequest.of(0, 10, Sort.by("id").descending()), 2);

        List<QuestionDto.Response> responses = List.of(new QuestionDto.Response(1L, "Spring Boot", "How to use spring boot", 0, 0, LocalDateTime.now(), LocalDateTime.now(), memberSimple1),
                new QuestionDto.Response(2L, "Java Spring Boot", "spring boot configuration", 0, 0, LocalDateTime.now(), LocalDateTime.now(), memberSimple2));

        given(questionService.findQuestionsByContent(Mockito.anyString(), Mockito.anyInt())).willReturn(questions);
        given(questionMapper.questionsToQuestionResponses(Mockito.anyList())).willReturn(responses);

        //when
        ResultActions actions = mockMvc.perform(get("/api/search").param("keyword", "\"spring boot\"").param("page", "1").param("size", "2").accept(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andDo(document("questions-searchByContent",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                List.of(
                                        parameterWithName("keyword").description("검색 키워드"),
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
                                fieldWithPath("data.[].member").type(JsonFieldType.OBJECT).description("작성자 데이터"),
                                fieldWithPath("data.[].member.memberId").type(JsonFieldType.NUMBER).description("작성자 식별자"),
                                fieldWithPath("data.[].member.memberName").type(JsonFieldType.STRING).description("작성자 이름"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("현재 사이즈"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("질문 전체 수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 개수")
                        )));
    }

    @Test
    public void 작성자검색_테스트() throws Exception {
        //given
        Member member1 = memberRepository.findByEmail("test@email.com").get();
        Member member2 = memberRepository.findByEmail("testA@email.com").get();
        QuestionDto.Response.MemberSimple memberSimple1 = new QuestionDto.Response.MemberSimple(member1.getId(), member1.getName());
        QuestionDto.Response.MemberSimple memberSimple2 = new QuestionDto.Response.MemberSimple(member2.getId(), member2.getName());

        Question question1 = getQuestion(member1);
        Question question2 = getQuestion2(member2);
        Question question3 = getQuestion(member1);

        Page<Question> questions = new PageImpl<>(List.of(question1, question2, question3),
                PageRequest.of(0, 10, Sort.by("id").descending()), 3);

        List<QuestionDto.Response> responses = List.of(new QuestionDto.Response(1L, "Spring Boot", "How to use spring boot", 0, 0, LocalDateTime.now(), LocalDateTime.now(), memberSimple1),
                new QuestionDto.Response(3L, "Java Spring Boot", "spring boot configuration", 0, 0, LocalDateTime.now(), LocalDateTime.now(), memberSimple1));

        given(questionService.findQuestions(Mockito.anyLong(), Mockito.anyInt())).willReturn(questions);
        given(questionMapper.questionsToQuestionResponses(Mockito.anyList())).willReturn(responses);

        //when
        ResultActions actions = mockMvc.perform(get("/api/search").param("keyword", "user:1").param("page", "1").param("size", "2").accept(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andDo(document("questions-searchByMemberId",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                List.of(
                                        parameterWithName("keyword").description("검색 키워드"),
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
                                fieldWithPath("data.[].member").type(JsonFieldType.OBJECT).description("작성자 데이터"),
                                fieldWithPath("data.[].member.memberId").type(JsonFieldType.NUMBER).description("작성자 식별자"),
                                fieldWithPath("data.[].member.memberName").type(JsonFieldType.STRING).description("작성자 이름"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("현재 사이즈"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("질문 전체 수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 개수")
                        )));
    }

    private Question getQuestion3(Member member) {
        Question question3 = new Question();
        question3.setId(3L);
        question3.setVote(0);
        question3.setView(0);
        question3.setContent("test content3");
        question3.setTitle("test title3");
        question3.setCreatedAt(LocalDateTime.now());
        question3.setLastModifiedAt(LocalDateTime.now());
        question3.setMember(member);
        return question3;
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

}