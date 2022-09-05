package com.seb39.mystackoverflow.controller;

import com.seb39.mystackoverflow.auth.PrincipalDetails;
import com.seb39.mystackoverflow.dto.AnswerDto;
import com.seb39.mystackoverflow.dto.MemberDto;
import com.seb39.mystackoverflow.dto.MultiResponseDto;
import com.seb39.mystackoverflow.dto.QuestionDto;
import com.seb39.mystackoverflow.entity.Answer;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.mapper.AnswerMapper;
import com.seb39.mystackoverflow.mapper.MemberMapper;
import com.seb39.mystackoverflow.mapper.QuestionMapper;
import com.seb39.mystackoverflow.service.AnswerService;
import com.seb39.mystackoverflow.service.MemberService;
import com.seb39.mystackoverflow.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final AnswerService answerService;
    private final AnswerMapper answerMapper;
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @GetMapping("/{memberId}/questions")
    private ResponseEntity getQuestions(@PathVariable Long memberId,
                                        @Positive @RequestParam(required = false, defaultValue = "1") int page) {
        Page<Question> questionPage = questionService.findQuestions(memberId, page - 1);
        List<Question> questions = questionPage.getContent();
        List<QuestionDto.Response> data = questionMapper.questionsToQuestionResponses(questions);
        return new ResponseEntity<>(new MultiResponseDto<>(data, questionPage), HttpStatus.OK);
    }

    @GetMapping("/{memberId}/answers")
    private ResponseEntity getAnswers(@PathVariable Long memberId,
                                      @Positive @RequestParam(required = false, defaultValue = "1") int page) {
        Page<Answer> answerPage = answerService.findAnswers(memberId, page - 1);
        List<Answer> answers = answerPage.getContent();
        List<AnswerDto.Response> data = answerMapper.answersToAnswerResponses(answers);
        return new ResponseEntity<>(new MultiResponseDto<>(data, answerPage), HttpStatus.OK);
    }
    @GetMapping("/me")
    public ResponseEntity myPage(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = memberService.findById(principalDetails.getMemberId());
        MemberDto.Response response = memberMapper.memberToMemberResponse(member);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
