package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.Comment;
import com.seb39.mystackoverflow.entity.Dtype;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final QuestionService questionService;


    public Comment createComment(Comment comment, long questionId, Long memberId) {
        Member member = memberService.findById(memberId);
        Question question = questionService.findQuestion(questionId);
        comment.setCommentMember(member);
        comment.setQuestionComment(question);
        comment.setDtype(Dtype.QUESTION);

        Comment savedComment = commentRepository.save(comment);
        return savedComment;
    }
}
