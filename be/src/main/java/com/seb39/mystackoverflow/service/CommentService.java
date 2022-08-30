package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.Comment;
import com.seb39.mystackoverflow.entity.Dtype;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import com.seb39.mystackoverflow.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Transactional
    public Comment updateComment(Comment comment, Long memberId) {
        Comment findComment = findComment(comment.getId());
        verifyWriter(memberId, findComment);


        Optional.ofNullable(comment.getContent())
                .ifPresent(findComment::setContent);

        return commentRepository.save(findComment);
    }

    @Transactional
    public void deleteComment(long id, long memberId) {
        Comment findComment = findComment(id);
        verifyWriter(memberId, findComment);

        commentRepository.delete(findComment);
    }

    private Comment findComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment findComment = optionalComment.orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));
        return findComment;
    }

    public void verifyWriter(Long memberId, Comment comment) {
        //작성자 ID
        Long writerId = comment.getMember().getId();

        if (writerId != memberId) {
            throw new RuntimeException("작성자가 아니면 수정 또는 삭제할 수 없습니다!");
        }
    }
}
