package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.Answer;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.repository.AnswerRepository;
import com.seb39.mystackoverflow.repository.MemberRepository;
import com.seb39.mystackoverflow.repository.QuestionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AnswerServiceTest {

    @Autowired AnswerService answerService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    QuestionRepository questionRepository;

    private String myEmail = "me@gmail.com";
    private String otherEmail = "other@gmail.com";

    @BeforeEach
    void beforeEach(){
        Member me = Member.builder()
                .email(myEmail)
                .password("1234")
                .name("SJ")
                .build();
        memberRepository.save(me);

        Member other = Member.builder()
                .email(otherEmail)
                .password("1234")
                .name("SJ")
                .build();
        memberRepository.save(other);

        Question question = new Question();
        question.setTitle("TEST QUESTION");
        question.setContent("TEST QUESTION's content");
        question.setQuestionMember(other);
        questionRepository.save(question);
    }

    @Test
    @DisplayName("특정 질문에 대해 답변을 생성한다.")
    void createAnswerTest(){
        // given
        Member member = memberRepository.findByEmail(myEmail).get();
        Question question = questionRepository.findAll().get(0);
        Answer answer = new Answer();
        answer.setContent("질문을 똑바로 올려주세요.");

        // when
        Long answerId = answerService.createAnswer(answer, question.getId(), member.getId());

        // then
        Answer findAnswer = answerService.findAnswer(answerId);
        assertThat(findAnswer.getContent()).isEqualTo("질문을 똑바로 올려주세요.");
        assertThat(findAnswer.getQuestion().getId()).isEqualTo(question.getId());
        assertThat(findAnswer.getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("본인이 작성한 답변을 수정한다.")
    void updateMyAnswerTest(){
        // given
        Member member = memberRepository.findByEmail(myEmail).get();
        Question question = questionRepository.findAll().get(0);
        Answer answer = new Answer();
        answer.setContent("질문을 똑바로 올려주세요.");
        Long answerId = answerService.createAnswer(answer, question.getId(), member.getId());

        // when
        Answer findAnswer = answerService.findAnswer(answerId);
        findAnswer.setContent("감사합니다");
        answerService.updateAnswer(answer,member.getId());

        // then
        assertThat(findAnswer.getContent()).isEqualTo("감사합니다");
    }

    @Test
    @DisplayName("본인이 작성하지 않은 답변은 수정할 수 없다.")
    void updateOtherAnswerTest(){
        // given
        Member member = memberRepository.findByEmail(myEmail).get();
        Member otherMember = memberRepository.findByEmail(otherEmail).get();
        Question question = questionRepository.findAll().get(0);
        Answer answer = new Answer();
        answer.setContent("질문을 똑바로 올려주세요.");
        Long answerId = answerService.createAnswer(answer, question.getId(), member.getId());

        // expected
        Answer findAnswer = answerService.findAnswer(answerId);
        findAnswer.setContent("감사합니다");

        assertThatThrownBy(()->answerService.updateAnswer(answer,otherMember.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("본인이 작성한 답변을 삭제한다.")
    void deleteMyAnswerTest(){
        // given
        Member member = memberRepository.findByEmail(myEmail).get();
        Question question = questionRepository.findAll().get(0);
        Answer answer = new Answer();
        answer.setContent("질문을 똑바로 올려주세요.");
        Long answerId = answerService.createAnswer(answer, question.getId(), member.getId());

        // when
        Answer findAnswer = answerService.findAnswer(answerId);
        findAnswer.setContent("감사합니다");
        answerService.deleteAnswer(answerId,member.getId());

        // then
        assertThatThrownBy(()->answerService.findAnswer(answerId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("본인이 작성하지 않은 답변은 삭제할 수 없다.")
    void deleteOtherAnswerTest(){
        // given
        Member member = memberRepository.findByEmail(myEmail).get();
        Member otherMember = memberRepository.findByEmail(otherEmail).get();
        Question question = questionRepository.findAll().get(0);
        Answer answer = new Answer();
        answer.setContent("질문을 똑바로 올려주세요.");
        Long answerId = answerService.createAnswer(answer, question.getId(), member.getId());

        // expected
        assertThatThrownBy(()->answerService.deleteAnswer(answerId,otherMember.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("특정 사용자의 답변들을 조회한다")
    void findAnswersTest(){
        // given
        Member member = memberRepository.findByEmail(myEmail).get();
        Question question = questionRepository.findAll().get(0);

        for(int i=0;i<40;i++){
            Answer answer = new Answer();
            answer.setContent("answer "+i);
            answerService.createAnswer(answer,question.getId(),member.getId());
        }

        // when
        Page<Answer> result = answerService.findAnswers(member.getId(), 0);
        List<Answer> answers = result.getContent();

        // then
        assertThat(result.getTotalElements()).isEqualTo(40);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(answers.size()).isEqualTo(30);
    }
}