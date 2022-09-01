package com.seb39.mystackoverflow.service;


import com.seb39.mystackoverflow.entity.Answer;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.repository.AnswerRepository;
import com.seb39.mystackoverflow.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionDetailsService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public Question findQuestionDetail(Long id){
        Question question = questionRepository.findQuestionWithCommentsById(id)
                .orElseThrow(()-> new IllegalArgumentException("Question not exist. id = "+ id));

        List<Answer> answers = answerRepository.findAnswerWithCommentsByQuestionId(id);
        question.setAnswers(answers);

        return question;
    }


}

