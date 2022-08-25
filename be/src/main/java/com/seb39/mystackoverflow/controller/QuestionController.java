package com.seb39.mystackoverflow.controller;

import com.seb39.mystackoverflow.dto.QuestionDto;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.mapper.QuestionMapper;
import com.seb39.mystackoverflow.service.QuestionService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    public QuestionController(QuestionService questionService, QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
    }

    //1. 질문 등록
    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionDto.Post requestBody) {
        Question question = questionMapper.questionPostToQuestion(requestBody);
        question.setTime();
        Question createdQuestion = questionService.createQuestion(question);

        Question response = questionMapper.questionToQuestionResponse(createdQuestion);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }
}
