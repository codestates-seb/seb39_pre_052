package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question createQuestion(Question question) {
        Question savedQuestion = questionRepository.save(question);
        return savedQuestion;
    }
}
