package com.seb39.mystackoverflow.repository;

import com.seb39.mystackoverflow.entity.Answer;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AnswerRepositoryTest {

    @Autowired AnswerRepository answerRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired QuestionRepository questionRepository;

    @Test
    public void findAllByMemberIdTest(){
        Member member = Member.builder()
                .email("abcd@naver.com")
                .name("SJ")
                .build();
        memberRepository.save(member);

        Question question = new Question();
        question.setTitle("title");
        question.setContent("content");
        question.changeMember(member);
        questionRepository.save(question);

        for(int i=0;i<100;i++){
            Answer answer = new Answer();
            answer.setMember(member);
            answer.changeQuestion(question);
            answer.setContent("Answer number " + i);
            answerRepository.save(answer);
        }

        Page<Answer> pageInfo = answerRepository.findAllByMemberId(member.getId(), PageRequest.of(0, 30, Sort.by("lastModifiedAt").descending()));
        System.out.println(pageInfo.getTotalElements());
        pageInfo.getContent()
                        .forEach(answer-> System.out.println(answer.getContent()));
    }
}