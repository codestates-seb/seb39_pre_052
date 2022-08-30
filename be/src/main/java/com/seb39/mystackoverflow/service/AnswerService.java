package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.Answer;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.repository.AnswerRepository;
import com.seb39.mystackoverflow.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final MemberService memberService;

    public Answer findAnswer(Long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("Answer not exist. id = " + answerId));
    }

    @Transactional
    public Long createAnswer(Answer answer, Long questionId, Long memberId) {
        Question question = questionService.findQuestion(questionId);
        Member member = memberService.findById(memberId);
        answer.setQuestion(question);
        answer.setMember(member);
        answerRepository.save(answer);
        return answer.getId();
    }

    @Transactional
    public void updateAnswer(Answer answer, Long memberId) {
        verifyWriter(memberId,answer.getId());
        Answer findAnswer = findAnswer(answer.getId());
        Optional.ofNullable(answer.getContent())
                .ifPresent(findAnswer::setContent);
    }

    @Transactional
    public void deleteAnswer(Long answerId, Long memberId){
        verifyWriter(memberId,answerId);
        answerRepository.deleteById(answerId);
    }

    private void verifyWriter(Long memberId, Long answerId) {
        Answer answer = findAnswer(answerId);
        Long writerId = answer.getMember().getId();
        if (!writerId.equals(memberId)) {
            String message = String.format("Member '%s' is not writer of answer '%s'", memberId, answer.getId());
            throw new IllegalArgumentException(message);
        }
    }
}
