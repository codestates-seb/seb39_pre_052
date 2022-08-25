package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Test
    public void 질문수정_테스트() {
        //given
        Question question = new Question();
        question.setContent("Hello Project!");
        question.setTitle("Hello Title");

        Question savedQuestion = questionService.createQuestion(question);
        //when
        String updateTitle = "update title test";

        question.setTitle(updateTitle);
        Question updatedQuestion = questionService.updateQuestion(question);
        //then
        assertEquals(updatedQuestion.getId(), savedQuestion.getId());
        assertEquals(updatedQuestion.getTitle(), updateTitle);
        assertEquals(updatedQuestion.getContent(), savedQuestion.getContent());
    }
}