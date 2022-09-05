package com.seb39.mystackoverflow.test;

import com.seb39.mystackoverflow.entity.*;
import com.seb39.mystackoverflow.repository.AnswerRepository;
import com.seb39.mystackoverflow.repository.CommentRepository;
import com.seb39.mystackoverflow.repository.MemberRepository;
import com.seb39.mystackoverflow.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;

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

}
