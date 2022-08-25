package com.seb39.mystackoverflow.repository;

import com.seb39.mystackoverflow.entity.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    //1. 질문 등록 테스트
    @Test
    public void 질문등록_test() {
        //given
        Question question = new Question();
        question.setContent("Hello Project!");
        question.setTitle("Hello Title");
        //when
        Question savedQuestion = questionRepository.save(question);

        //then
//        System.out.println("===============question===============");
//        System.out.println(savedQuestion);
//        System.out.println(savedQuestion.getCreatedAt());

        Assertions.assertEquals(savedQuestion.getContent(), question.getContent());
        Assertions.assertEquals(savedQuestion.getTitle(), question.getTitle());
    }

}