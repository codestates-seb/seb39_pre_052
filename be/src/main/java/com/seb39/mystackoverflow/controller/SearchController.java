package com.seb39.mystackoverflow.controller;


import com.seb39.mystackoverflow.dto.MultiResponseDto;
import com.seb39.mystackoverflow.dto.QuestionDto;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.mapper.QuestionMapper;
import com.seb39.mystackoverflow.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @GetMapping
    public ResponseEntity<MultiResponseDto<QuestionDto.Response>> searchQuestions(@RequestParam(required = false) String keyword,
                                          @Positive @RequestParam int page,
                                          @Positive @RequestParam int size) {
        Page<Question> questionPage = null;

        if (keyword == null) {
            questionPage = questionService.findQuestions(page - 1, size);
        }
        else if (Arrays.stream(keyword.split(" ")).anyMatch(s -> s.startsWith("\""))) {
            keyword = keyword.substring(keyword.indexOf("\"") + 1, keyword.lastIndexOf("\""));
            questionPage = questionService.findQuestionsByContent(keyword, page - 1);
        }
        else if (Arrays.stream(keyword.split(" ")).anyMatch(s -> s.startsWith("user:"))) {
            StringBuilder sb = new StringBuilder();
            keyword = keyword.substring(keyword.indexOf(":") + 1);
            for (String s : keyword.split("")) {
                if(s != null && s.matches("[0-9]+")){
                    sb.append(s);
                }
            }
            long memberId = Long.parseLong(sb.toString());
            questionPage = questionService.findQuestions(memberId, page - 1);
        }
        else {
            questionPage = questionService.findQuestionsByTitle(keyword, page - 1);
        }
        List<QuestionDto.Response> responses = questionMapper.questionsToQuestionResponses(questionPage.getContent());
        return new ResponseEntity<>(new MultiResponseDto<>(responses, questionPage), HttpStatus.OK);
    }
}
