package com.seb39.mystackoverflow.test;

import com.seb39.mystackoverflow.dto.QuestionDetailDto;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @GetMapping("/test/question")
    //질문 리스트 데이터 송신 테스트
    public ResponseEntity test(@RequestParam int page,
                               @RequestParam int size) {
        List<TestDto> memberList = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            memberList.add(TestDto.builder()
                    .content("test content" + i)
                    .name("member" + i)
                    .title("test title" + i)
                    .createdAt(LocalDateTime.now())
                    .view(i)
                    .vote(i)
                    .answered(i)
                    .build());
        }
        int total = memberList.size() / size;
        PageRequest request = PageRequest.of(page - 1, size);
        Page<TestDto> dtoPage = new PageImpl<>(memberList);

        Page<TestDto> testDtoPage = new PageImpl<TestDto>(memberList, request, memberList.size());
        return new ResponseEntity(new TestMultiResponseDto(memberList, testDtoPage), HttpStatus.OK);
    }


    @GetMapping("/test/question/1")
    public QuestionDetailDto testQuestionDetail() {

        QuestionDetailDto.Member member1 = new QuestionDetailDto.Member(1L, "USERAAA");
        QuestionDetailDto.Member member2 = new QuestionDetailDto.Member(2L, "USERBBB");
        QuestionDetailDto.Member member3 = new QuestionDetailDto.Member(3L, "USERCCC");


        QuestionDetailDto dto = new QuestionDetailDto();
        dto.setId(1L);
        dto.setTitle("Test Question");
        dto.setContent("test content...");
        dto.setAskedAt(LocalDateTime.now());
        dto.setView(754625);
        dto.setVote(2247);
        dto.setMember(member1);

        dto.setComments(List.of(
                new QuestionDetailDto.Comment(23L, "Question comment 1", member2),
                new QuestionDetailDto.Comment(25L, "Question comment 2", member3)
        ));

        QuestionDetailDto.Answer answer1 = new QuestionDetailDto.Answer();
        answer1.setId(4L);
        answer1.setContent("Test Answer 01");
        answer1.setAnsweredAt(LocalDateTime.now());
        answer1.setMember(member2);
        answer1.setComments(List.of(
                new QuestionDetailDto.Comment(23L, "Answer comment 1", member1),
                new QuestionDetailDto.Comment(25L, "Answer comment 2", member3)
        ));

        QuestionDetailDto.Answer answer2 = new QuestionDetailDto.Answer();
        answer2.setId(223L);
        answer2.setContent("Test Answer 02");
        answer2.setAnsweredAt(LocalDateTime.now());
        answer2.setMember(member2);
        answer2.setComments(List.of());

        dto.setAnswers(List.of(
                answer1,
                answer2
        ));

        return dto;
    }
}
