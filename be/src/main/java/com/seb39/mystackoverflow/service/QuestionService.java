package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberService memberService;

    public Question findQuestion(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        return optionalQuestion.orElseThrow(
                () -> new IllegalArgumentException("해당 질문을 찾을 수 없습니다."));
    }

    @Transactional
    public Question createQuestion(Question question, Long memberId) {
        Member findMember = memberService.findById(memberId);
        question.setMember(findMember);
        System.out.println("question = " + question.getMember().getId());
        Question savedQuestion = questionRepository.save(question);
        return savedQuestion;
    }

    @Transactional
    public Question updateQuestion(Question question, Long memberId) {
        Question findQuestion = findQuestion(question.getId());
        verifyWriter(memberId, findQuestion);

        Optional.ofNullable(question.getTitle())
                .ifPresent(findQuestion::setTitle);

        Optional.ofNullable(question.getContent())
                .ifPresent(findQuestion::setContent);

        return questionRepository.save(findQuestion);
    }

    @Transactional
    public void delete(long id, long memberId) {
        Question findQuestion = findQuestion(id);
        verifyWriter(memberId, findQuestion);
        questionRepository.delete(findQuestion);
    }

    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    public Page<Question> findQuestions(Long memberId, int page) {
        return questionRepository.findAllByMemberId(memberId, PageRequest.of(page, 30, Sort.by("id").descending()));
    }

    public Page<Question> findQuestionsByTitle(String keyword, int page) {
        return questionRepository.findByTitleContainingIgnoreCase(keyword, PageRequest.of(page, 30, Sort.by("createdAt").descending()));
    }

    public Page<Question> findQuestionsByContent(String keyword, int page) {
        return questionRepository.findByContentContainingIgnoreCase(keyword, PageRequest.of(page, 30, Sort.by("createdAt").descending()));
    }

    public void verifyWriter(Long memberId, Question question) {
        Long writerId = question.getMember().getId();
        if (writerId != memberId) {
            throw new RuntimeException("작성자가 아니면 수정 또는 삭제할 수 없습니다!");
        }
    }
}
