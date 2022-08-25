package com.seb39.mystackoverflow.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
public class TestController {

    @GetMapping("/test/question")
    //질문 리스트 데이터 송신 테스트
    public @ResponseBody TestDto test() {
        TestDto dto = TestDto.builder()
                .content("test content")
                .name("memberA")
                .title("test title")
                .createdAt(LocalDateTime.now())
                .view(0)
                .vote(0)
                .build();
        return dto;
    }
}
