package com.seb39.mystackoverflow.service;


import com.seb39.mystackoverflow.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionDetailsService {

    private final QuestionRepository questionRepository;
    private final AnswerService answerService;
    private final CommentService commentService;


}

