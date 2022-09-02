package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.*;
import com.seb39.mystackoverflow.repository.AnswerRepository;
import com.seb39.mystackoverflow.repository.CommentRepository;
import com.seb39.mystackoverflow.repository.MemberRepository;
import com.seb39.mystackoverflow.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
class QuestionDetailsServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    QuestionDetailService questionDetailsService;

    @Autowired
    EntityManager em;


    @BeforeEach
    void beforeEach(){

        Member[] members = new Member[4];
        for(int i=0;i<4;i++){
            String name = ""+i+i+i;
            members[i] = Member.builder()
                    .email(name+"@gmail.com")
                    .name(name)
                    .build();
            memberRepository.save(members[i]);
        }

        Question question = new Question();
        question.setTitle("Test Question");
        question.setContent("Test Question Content");
        question.setView(1111);
        question.setVote(11);
        question.setMember(members[0]);

        questionRepository.save(question);

        Answer[] answers = new Answer[2];
        for(int i=0;i<2;i++){
            answers[i] = Answer.builder()
                    .content("ANSWER0"+i)
                    .question(question)
                    .member(members[i+1])
                    .build();
            answerRepository.save(answers[i]);
        }

        for(int i=0;i<2;i++){
            Comment comment = new Comment();
            comment.setPostType(PostType.QUESTION);
            comment.setContent("Question comment "+i);
            comment.setMember(members[i]);
            comment.changePost(PostType.QUESTION,question);
            commentRepository.save(comment);
        }

        for(int i=0;i<8;i++){
            Comment comment = new Comment();
            comment.setPostType(PostType.ANSWER);
            comment.setContent("Answer comment "+i);
            comment.setMember(members[i%4]);
            comment.changePost(PostType.ANSWER,answers[i%2]);
            commentRepository.save(comment);
        }

        em.flush();
        em.clear();
    }

    @Test
    void questionWithCommentTest(){

        Question q = questionRepository.findAll().get(0);
        Long questionId = q.getId();

        em.flush();
        em.clear();

        Question question = questionRepository.findQuestionWithCommentsById(questionId).get();

        System.out.println("question id = " + question.getId() + " member " + question.getMember().getId() + " " + question.getMember().getName());

        List<Comment> comments = question.getComments();
        for (Comment comment : comments) {
            System.out.println("comment : "+comment.getContent() + " member " + comment.getMember().getId() + " " + comment.getMember().getName());
        }
    }

    @Test
    void answerWithCommentsTest(){
        List<Answer> result = answerRepository.findAnswerWithCommentsByQuestionId(1L);
        for (Answer answer : result) {
            System.out.println("answer = " + answer.getContent() + " member " + answer.getMember().getId() + " " + answer.getMember().getName());
            List<Comment> comments = answer.getComments();
            for (Comment comment : comments) {
                System.out.println("  comment = " + comment.getContent() + " member " + comment.getMember().getId() + " " + comment.getMember().getName());
            }
        }
    }

    @Test
    void findQuestionDetailTest(){
        Question q = questionRepository.findAll().get(0);
        Long questionId = q.getId();

        em.flush();
        em.clear();

        Question question = questionDetailsService.findQuestionDetail(questionId);
        System.out.println("question = " + question);
        System.out.println("  question comments = " +question.getComments());
        List<Answer> answers = question.getAnswers();
        for (Answer answer : answers) {
            System.out.println("  answer = " + answer);
            System.out.println("    answer comments = " + answer.getComments());
        }
    }
}