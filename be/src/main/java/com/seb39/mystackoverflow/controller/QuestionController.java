package com.seb39.mystackoverflow.controller;

import com.seb39.mystackoverflow.auth.PrincipalDetails;
import com.seb39.mystackoverflow.dto.MultiResponseDto;
import com.seb39.mystackoverflow.dto.QuestionDetailDto;
import com.seb39.mystackoverflow.dto.QuestionDto;
import com.seb39.mystackoverflow.dto.SingleResponseDto;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.mapper.QuestionDetailMapper;
import com.seb39.mystackoverflow.mapper.QuestionMapper;
import com.seb39.mystackoverflow.service.QuestionDetailService;
import com.seb39.mystackoverflow.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@Slf4j
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    private final QuestionDetailService questionDetailService;
    private final QuestionDetailMapper questionDetailMapper;


    @GetMapping("/{id}")
    public ResponseEntity<SingleResponseDto<QuestionDetailDto>> getQuestionDetail(@PathVariable Long id){
        Question question = questionDetailService.findQuestionDetail(id);
        QuestionDetailDto questionDetail = questionDetailMapper.questionToQuestionDetail(question);
        return new ResponseEntity<>(new SingleResponseDto<>(questionDetail), HttpStatus.OK);
    }

    @PostMapping
    @Secured("ROLE_USER")
    public ResponseEntity<SingleResponseDto<QuestionDto.Response>> postQuestion(@Valid @RequestBody QuestionDto.Post requestBody,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Question question = questionMapper.questionPostToQuestion(requestBody);
        Long memberId = principalDetails.getMemberId();
        Question createdQuestion = questionService.createQuestion(question, memberId);

        QuestionDto.Response response = questionMapper.questionToQuestionResponse(createdQuestion);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<SingleResponseDto<QuestionDto.Response>> patchQuestion(
            @PathVariable("id") @Positive long id,
            @Valid @RequestBody QuestionDto.Patch requestBody,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        requestBody.setId(id);

        Long memberId = principalDetails.getMemberId();

        Question updateQuestion = questionService.updateQuestion(questionMapper.questionPatchToQuestion(requestBody), memberId);

        QuestionDto.Response response = questionMapper.questionToQuestionResponse(updateQuestion);


        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<String> deleteQuestion(@PathVariable("id") @Positive long id,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMemberId();
        questionService.delete(id, memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<MultiResponseDto<QuestionDto.Response>> getQuestions(@Positive @RequestParam int page,
                                       @Positive @RequestParam int size
    ) {
        Page<Question> questionPage = questionService.findQuestions(page - 1, size);
        List<QuestionDto.Response> responses = questionMapper.questionsToQuestionResponses(questionPage.getContent());
        return new ResponseEntity<>(new MultiResponseDto<>(responses, questionPage), HttpStatus.OK);
    }
}
