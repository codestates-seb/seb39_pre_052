package com.seb39.mystackoverflow.service;

import com.seb39.mystackoverflow.entity.*;
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
    private final AnswerService answerService;


    @Transactional
    public Comment createComment(Comment comment, PostType postType, Long postId, Long memberId){
        Member member = memberService.findById(memberId);
        comment.setMember(member);

        if(postType == PostType.QUESTION)
            return createCommentOnQuestion(comment, postId, member);
        if(postType == PostType.ANSWER)
            return createCommentOnAnswer(comment,postId,member);

        throw new UnsupportedOperationException("Unsupported post type. PostType = "+ postType);
    }

    private Comment createCommentOnQuestion(Comment comment, Long questionId, Member member) {
        Question question = questionService.findQuestion(questionId);
        comment.changePost(PostType.QUESTION, question);
        return commentRepository.save(comment);
    }

    private Comment createCommentOnAnswer(Comment comment, Long answerId, Member member) {
        Answer answer = answerService.findAnswer(answerId);
        comment.changePost(PostType.ANSWER, answer);
        return commentRepository.save(comment);
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
        return optionalComment.orElseThrow(
                () -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));
    }

    public void verifyWriter(Long memberId, Comment comment) {
        //작성자 ID
        Long writerId = comment.getMember().getId();

        if (!writerId.equals(memberId)) {
            throw new RuntimeException("작성자가 아니면 수정 또는 삭제할 수 없습니다!");
        }
    }
}
