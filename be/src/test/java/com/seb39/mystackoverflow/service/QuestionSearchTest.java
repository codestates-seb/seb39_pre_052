package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class QuestionSearchTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private MemberService memberService;

    @Test
    public void 질문제목검색_테스트() {
        //given
        Member member = Member.builder()
                .email("email@naver.com")
                .name("nameA")
                .password("passwordA")
                .roles("ROLE_USER")
                .build();
        memberService.signUp(member);
        Member findMember = memberService.findByEmail("email@naver.com");

        Question question1 = new Question();
        question1.setTitle("Java Spring");
        question1.setContent("contentA");
        question1.setQuestionMember(findMember);
        questionService.createQuestion(question1, findMember.getId());
        //when
        Page<Question> questions = questionService.findQuestionsByTitle("java", 0);

        List<Question> content = questions.getContent();
        //then
        Assertions.assertEquals(content.size(), 1);
    }

    @Test
    public void 질문제목_질문내용검색_테스트() {
        //given
        Member member = Member.builder()
                .email("email@naver.com")
                .name("nameA")
                .password("passwordA")
                .roles("ROLE_USER")
                .build();
        memberService.signUp(member);
        Member findMember = memberService.findByEmail("email@naver.com");

        Question question1 = new Question();
        question1.setTitle("Java Spring");
        question1.setContent("contentA");
        question1.setQuestionMember(findMember);
        questionService.createQuestion(question1, findMember.getId());
        //when
        Page<Question> questions = questionService.findQuestionByTitleAndContent("java", 0);
        List<Question> content = questions.getContent();
        //then
        Assertions.assertEquals(content.size(), 1);
    }

}