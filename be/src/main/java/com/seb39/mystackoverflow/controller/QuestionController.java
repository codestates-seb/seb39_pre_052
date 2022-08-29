package com.seb39.mystackoverflow.controller;

import com.seb39.mystackoverflow.auth.PrincipalDetails;
import com.seb39.mystackoverflow.dto.MultiResponseDto;
import com.seb39.mystackoverflow.dto.QuestionDto;
import com.seb39.mystackoverflow.dto.SingleReponseDto;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.mapper.QuestionMapper;
import com.seb39.mystackoverflow.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@Slf4j
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    //로그인한 회원 정보를 가져오기 위해 PrincipalDetails DI
//    private final PrincipalDetails principalDetails;

    public QuestionController(QuestionService questionService, QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
//        this.principalDetails = principalDetails;
    }

    //1. 질문 등록
    //Member Entity와 연관관계 매핑 후, 작성자에 대한 정보를 입력하는 로직 구현 예정
    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionDto.Post requestBody,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Question question = questionMapper.questionPostToQuestion(requestBody);
        question.setCreateTime();
        Member member = principalDetails.getMember();
        Question createdQuestion = questionService.createQuestion(question, member);

        QuestionDto.response response = questionMapper.questionToQuestionResponse(createdQuestion);

        return new ResponseEntity<>(new SingleReponseDto<>(response), HttpStatus.CREATED);
    }

    //2. 질문 수정
    @PatchMapping("/{id}")
    public ResponseEntity patchQuestion(
            @PathVariable("id") @Positive long id,
            @Valid @RequestBody QuestionDto.Patch requestBody) {
        requestBody.setId(id);

        Question updateQuestion = questionService.updateQuestion(questionMapper.questionPatchToQuestion(requestBody));
        updateQuestion.setUpdateTime();

        QuestionDto.response response = questionMapper.questionToQuestionResponse(updateQuestion);


        return new ResponseEntity(response, HttpStatus.OK);
    }

    //3. 질문 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity deleteQuestion(@PathVariable("id") @Positive long id) {
        questionService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //4. 질문 전체 조회
    //Member Entity와 연관관계 매핑 후, 질문을 전체 조회할 때 Member와 관련된 정보도 포함할 수 있도록 수정할 예정
    @GetMapping
    public ResponseEntity getQuestions(@Positive @RequestParam int page,
                                       @Positive @RequestParam int size) {
        Page<Question> questionPage = questionService.findQuestions(page - 1, size);
        List<Question> questions = questionPage.getContent();


        return new ResponseEntity(new MultiResponseDto(questions, questionPage), HttpStatus.OK);
    }
}
