package com.seb39.mystackoverflow.repository;

import com.seb39.mystackoverflow.entity.Answer;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnswerRepositoryTest {

    @Autowired AnswerRepository answerRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired QuestionRepository questionRepository;

    @Test
    public void findAllByMemberIdTest(){
        Member member = Member.builder()
                .email("abcd@naver.com")
                .name("SJ")
                .build();
        memberRepository.save(member);

        Question question = new Question();
        question.setTitle("title");
        question.setContent("content");
        question.setQuestionMember(member);
        questionRepository.save(question);

        Answer answer = new Answer();


    }

}