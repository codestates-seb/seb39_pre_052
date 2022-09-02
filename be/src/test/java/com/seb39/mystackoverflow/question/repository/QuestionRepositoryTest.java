package com.seb39.mystackoverflow.question.repository;

import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.repository.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("질문 등록 테스트")
    public void 질문등록_test() {
        //given
        Question question = new Question();
        question.setContent("Hello Project!");
        question.setTitle("Hello Title");
        //when
        Question savedQuestion = questionRepository.save(question);

        //then
        Assertions.assertEquals(savedQuestion.getContent(), question.getContent());
        Assertions.assertEquals(savedQuestion.getTitle(), question.getTitle());
    }



}