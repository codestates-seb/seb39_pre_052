package com.seb39.mystackoverflow.controller;

import com.seb39.mystackoverflow.dto.AnswerDto;
import com.seb39.mystackoverflow.dto.MultiResponseDto;
import com.seb39.mystackoverflow.entity.Answer;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.mapper.AnswerMapper;
import com.seb39.mystackoverflow.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{memberId}/answers")
    private ResponseEntity getAnswers(@PathVariable Long memberId, @Positive @RequestParam(required = false, defaultValue = "1") int page) {
        Page<Answer> answerPage = answerService.findAnswers(memberId, page - 1);
        List<AnswerDto.Response> data = answerPage.getContent()
                .stream()
                .map(answerMapper::answerToAnswerResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new MultiResponseDto<>(data, answerPage), HttpStatus.OK);
    }
}
