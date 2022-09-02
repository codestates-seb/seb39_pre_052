package com.seb39.mystackoverflow.controller;

import com.seb39.mystackoverflow.auth.PrincipalDetails;
import com.seb39.mystackoverflow.dto.MultiResponseDto;
import com.seb39.mystackoverflow.dto.QuestionDto;
import com.seb39.mystackoverflow.dto.SingleResponseDto;
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
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@Slf4j
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    public QuestionController(QuestionService questionService, QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
    }

    //1. 질문 등록
    //Member Entity와 연관관계 매핑 후, 작성자에 대한 정보를 입력하는 로직 구현 예정
    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionDto.Post requestBody,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Question question = questionMapper.questionPostToQuestion(requestBody);
        Long memberId = principalDetails.getMemberId();
        Question createdQuestion = questionService.createQuestion(question, memberId);


        QuestionDto.Response response = questionMapper.questionToQuestionResponse(createdQuestion);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    //2. 질문 수정
    @PatchMapping("/{id}")
    public ResponseEntity patchQuestion(
            @PathVariable("id") @Positive long id,
            @Valid @RequestBody QuestionDto.Patch requestBody,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        requestBody.setId(id);
        //로그인된 회원 ID
        Long memberId = principalDetails.getMemberId();

        Question updateQuestion = questionService.updateQuestion(questionMapper.questionPatchToQuestion(requestBody), memberId);

        QuestionDto.Response response = questionMapper.questionToQuestionResponse(updateQuestion);


        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    //3. 질문 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity deleteQuestion(@PathVariable("id") @Positive long id,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMemberId();
        questionService.delete(id, memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //4. 질문 전체 조회
    //Member Entity와 연관관계 매핑 후, 질문을 전체 조회할 때 Member와 관련된 정보도 포함할 수 있도록 수정할 예정
    @GetMapping
    public ResponseEntity getQuestions(@RequestParam(required = false) String keyword,
                                       @Positive @RequestParam int page,
                                       @Positive @RequestParam int size
    ) {
        //(Optional)4. 검색어가 score: 로 시작 => score:{scoreNum} score가 scoreNum 이상인 질문 검색
        Page<Question> questionPage = null;

        if (keyword == null) {
            questionPage = questionService.findQuestions(page - 1, size);
        }
        //2. 검색어에 "" 존재 => 내용으로 검색
        else if (Arrays.stream(keyword.split(" ")).anyMatch(s -> s.startsWith("\""))) {
            keyword = keyword.substring(keyword.indexOf("\"") + 1, keyword.lastIndexOf("\""));
            questionPage = questionService.findQuestionsByContent(keyword, page - 1);
            /**
             * 검색어와 완전히 일치하지 않으면 검색되지 않는 문제점 존재
             */

        }
        //3. 검색어가 user: 로 시작 => user:{id} id에 해당하는 사용자가 올린 질문 검색
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
        //1. 검색 기본 => 제목으로 검색
        else {
            questionPage = questionService.findQuestionsByTitle(keyword, page - 1);
        }
        List<QuestionDto.Response> responses = questionMapper.questionsToQuestionResponses(questionPage.getContent());
        return new ResponseEntity<>(new MultiResponseDto<>(responses, questionPage), HttpStatus.OK);
    }
}
