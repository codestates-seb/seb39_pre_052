package com.seb39.mystackoverflow.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @GetMapping("/test/question")
    //질문 리스트 데이터 송신 테스트
    public @ResponseBody
    List<TestDto> test() {
        List<TestDto> memberList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
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
        return memberList;
    }
}
