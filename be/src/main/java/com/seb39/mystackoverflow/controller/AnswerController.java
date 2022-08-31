package com.seb39.mystackoverflow.controller;

import com.seb39.mystackoverflow.auth.PrincipalDetails;
import com.seb39.mystackoverflow.dto.AnswerDto;
import com.seb39.mystackoverflow.entity.Answer;
import com.seb39.mystackoverflow.mapper.AnswerMapper;
import com.seb39.mystackoverflow.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerMapper mapper;

    @PostMapping
    @Secured("ROLE_USER")
    public ResponseEntity postAnswer(@RequestParam("question-id") Long questionId, @Valid @RequestBody AnswerDto.Post requestDto, @AuthenticationPrincipal PrincipalDetails principal) {
        Answer answer = mapper.answerPostToAnswer(requestDto);
        Long memberId = principal.getMemberId();
        answerService.createAnswer(answer,questionId,memberId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{answerId}")
    @Secured("ROLE_USER")
    public ResponseEntity patchAnswer(@PathVariable Long answerId, @Valid @RequestBody AnswerDto.Patch requestDto, @AuthenticationPrincipal PrincipalDetails principal){
        Answer answer = mapper.answerPatchToAnswer(answerId,requestDto);
        Long memberId = principal.getMemberId();
        answerService.updateAnswer(answer,memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{answerId}")
    @Secured("ROLE_USER")
    public ResponseEntity deleteAnswer(@PathVariable Long answerId, @AuthenticationPrincipal PrincipalDetails principal){
        Long memberId = principal.getMemberId();
        answerService.deleteAnswer(answerId,memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
