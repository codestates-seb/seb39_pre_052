package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    //1. 질문 등록
    public Question createQuestion(Question question) {
        Question savedQuestion = questionRepository.save(question);
        return savedQuestion;
    }

    //2. 질문 수정
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Question updateQuestion(Question question) {
        Question findQuestion = findQuestion(question);

        Optional.ofNullable(question.getTitle())
                .ifPresent(findQuestion::setTitle);

        Optional.ofNullable(question.getContent())
                .ifPresent(findQuestion::setContent);

        return questionRepository.save(findQuestion);
    }

    //3. 질문 삭제
    public void delete(long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);

        Question question = optionalQuestion.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다."));//추후 Exception 수정?

        questionRepository.delete(question);
    }

    //4. 질문 전체 조회
    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    //해당 질문이 존재하는지 확인하는 메서드
    @Transactional(readOnly = true)
    public Question findQuestion(Question question) {
        Optional<Question> optionalQuestion = questionRepository.findById(question.getId());

        Question findQuestion = optionalQuestion.orElseThrow(() -> new IllegalArgumentException("해당 질문을 찾을 수 없습니다."));

        return findQuestion;
    }

}
