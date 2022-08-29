package com.seb39.mystackoverflow.question.service;

import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.repository.MemberRepository;
import com.seb39.mystackoverflow.repository.QuestionRepository;
import com.seb39.mystackoverflow.service.MemberService;
import com.seb39.mystackoverflow.service.QuestionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void 데이터_준비() {
        Question question = new Question();
        question.setContent("Hello Project!");
        question.setTitle("Hello Title");

        Member member = Member.builder()
                .email("test@naver.com")
                .name("testA")
                .password("passwordA")
                .phone("010-1234-1234")
                .username("usernameA")
                .roles("ROLE_USER")
                .build();

        memberRepository.save(member);

        questionService.createQuestion(question, member.getId());
    }


    @Test
    public void 질문수정_테스트() {
        //given
//        Question question = new Question();
//        question.setContent("Hello Project!");
//        question.setTitle("Hello Title");
//
//        Question savedQuestion = questionService.createQuestion(question);
        Question question = questionRepository.findById(2L).get();
        //when
        String updateTitle = "update title test";

        question.setTitle(updateTitle);
//        Question updatedQuestion = questionService.updateQuestion(question);
        //then
//        assertEquals(updatedQuestion.getId(), question.getId());
//        assertEquals(updatedQuestion.getTitle(), updateTitle);
//        assertEquals(updatedQuestion.getContent(), question.getContent());
    }
    @Test
    public void 질문수정_회원연동_테스트() {

    }


    @Test
    public void 질문삭제_테스트() {
        //given
//        Question question = new Question();
//        question.setContent("Hello Project!");
//        question.setTitle("Hello Title");
//
//        Question savedQuestion = questionService.createQuestion(question);
        Question question = questionRepository.findById(1L).get();
        //when
        questionService.delete(question.getId());
        List<Question> questionList = questionRepository.findAll();
        //then
        assertEquals(questionList.size(), 0);

    }

//    @Test
    public void 질문전체조회_테스트() {
        //given
        Question question = new Question();
        question.setContent("Hello Project2!");
        question.setTitle("Hello Title2");
//        questionService.createQuestion(question, 2L);
        //when
        List<Question> questions = questionService.findQuestions(0, 2).getContent();
        //then
        assertEquals(questions.size(), 2);
    }
}