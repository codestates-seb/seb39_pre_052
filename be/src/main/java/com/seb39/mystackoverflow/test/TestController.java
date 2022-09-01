package com.seb39.mystackoverflow.test;

import com.seb39.mystackoverflow.dto.QuestionDetailDto;
import com.seb39.mystackoverflow.entity.*;
import com.seb39.mystackoverflow.repository.AnswerRepository;
import com.seb39.mystackoverflow.repository.CommentRepository;
import com.seb39.mystackoverflow.repository.MemberRepository;
import com.seb39.mystackoverflow.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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


    @GetMapping("/test/init")
    public String init(){
        Member member1 = Member.builder()
                .email("AAA@gmail.com")
                .name("AAA")
                .build();

        Member member2 = Member.builder()
                .email("BBB@gmail.com")
                .name("BBB")
                .build();

        Member member3 = Member.builder()
                .email("CCC@gmail.com")
                .name("CCC")
                .build();

        Member member4 = Member.builder()
                .email("DDD@gmail.com")
                .name("DDD")
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        Question question = new Question();
        question.setTitle("Test Question");
        question.setContent("Test Question Content");
        question.setView(1111);
        question.setVote(11);
        question.setMember(member1);

        questionRepository.save(question);

        Answer answer1 = Answer.builder()
                .content("ANSWER01")
                .question(question)
                .member(member2)
                .build();

        Answer answer2 = Answer.builder()
                .content("ANSWER02")
                .question(question)
                .member(member3)
                .build();

        answerRepository.save(answer1);
        answerRepository.save(answer2);

        Comment comment1 = new Comment();
        comment1.setPostType(PostType.QUESTION);
        comment1.setContent("Question comment 1");
        comment1.setMember(member2);
        comment1.changePost(PostType.QUESTION,question);

        Comment comment2 = new Comment();
        comment2.setPostType(PostType.ANSWER);
        comment2.setContent("Answer comment 1");
        comment2.setMember(member1);
        comment2.changePost(PostType.ANSWER,answer1);

        Comment comment3 = new Comment();
        comment3.setPostType(PostType.ANSWER);
        comment3.setContent("Answer comment 2");
        comment3.setMember(member3);
        comment3.changePost(PostType.ANSWER,answer2);

        Comment comment4 = new Comment();
        comment4.setPostType(PostType.QUESTION);
        comment4.setContent("Question comment 2");
        comment4.setMember(member3);
        comment4.changePost(PostType.QUESTION,question);

        Comment comment5 = new Comment();
        comment5.setPostType(PostType.QUESTION);
        comment5.setContent("Question comment 3");
        comment5.setMember(member3);
        comment5.changePost(PostType.QUESTION,question);

        Comment comment6 = new Comment();
        comment6.setPostType(PostType.ANSWER);
        comment6.setContent("Answer comment 6");
        comment6.setMember(member2);
        comment6.changePost(PostType.ANSWER,answer1);

        Comment comment7 = new Comment();
        comment7.setPostType(PostType.ANSWER);
        comment7.setContent("Answer comment 5");
        comment7.setMember(member1);
        comment7.changePost(PostType.ANSWER,answer1);

        Comment comment8 = new Comment();
        comment8.setPostType(PostType.ANSWER);
        comment8.setContent("Answer comment 4");
        comment8.setMember(member2);
        comment8.changePost(PostType.ANSWER,answer2);

        Comment comment9 = new Comment();
        comment9.setPostType(PostType.ANSWER);
        comment9.setContent("Answer comment 3");
        comment9.setMember(member3);
        comment9.changePost(PostType.ANSWER,answer2);

        Comment comment10 = new Comment();
        comment10.setPostType(PostType.ANSWER);
        comment10.setContent("Answer comment 7");
        comment10.setMember(member4);
        comment10.changePost(PostType.ANSWER,answer2);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);
        commentRepository.save(comment5);
        commentRepository.save(comment6);
        commentRepository.save(comment7);
        commentRepository.save(comment8);
        commentRepository.save(comment9);
        commentRepository.save(comment10);
        return "ok";
    }
}
