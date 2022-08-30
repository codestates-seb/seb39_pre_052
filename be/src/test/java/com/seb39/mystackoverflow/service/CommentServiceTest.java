package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.Comment;
import com.seb39.mystackoverflow.entity.Dtype;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private QuestionService questionService;

    @Test
    public void 댓글생성_테스트() {
        //given
        Member member = new Member();
        member.setEmail("test@naver.com");
        member.setName("nameA");
        member.setPassword("passwordA");
        member.setRoles("USER");
        memberService.signUp(member);

        Question question = new Question();
        question.setTitle("titleA");
        question.setView(0);
        question.setVote(0);
        question.setContent("contentA");
        question.setQuestionMember(member);
        questionService.createQuestion(question, question.getMember().getId());

        Comment comment = new Comment();
        comment.setContent("Comment Content");
        comment.setDtype(Dtype.QUESTION);
        //when
        commentService.createComment(comment, question.getId(), member.getId());
        //then
        assertEquals(comment.getMember().getId(), member.getId());
        assertEquals(comment.getQuestion().getId(), question.getId());
    }
}