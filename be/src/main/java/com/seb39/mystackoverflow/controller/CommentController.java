package com.seb39.mystackoverflow.controller;

import com.seb39.mystackoverflow.auth.PrincipalDetails;
import com.seb39.mystackoverflow.dto.CommentDto;
import com.seb39.mystackoverflow.dto.SingleResponseDto;
import com.seb39.mystackoverflow.entity.Comment;
import com.seb39.mystackoverflow.mapper.CommentMapper;
import com.seb39.mystackoverflow.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentMapper commentMapper;
    private final CommentService commentService;

    //1. 댓글 등록(질문글)
    @PostMapping("/{questionId}/comment")
    public ResponseEntity postComment(
            @PathVariable("questionId") @Positive long questionId,
            @Valid @RequestBody CommentDto.Post requestBody,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Comment comment = commentMapper.commentPostToComment(requestBody);
        Long memberId = principalDetails.getMemberId();
        Comment createdComment = commentService.createComment(comment, questionId, memberId);

        CommentDto.Response response = commentMapper.commentToCommentResponse(createdComment);

        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    //2. 댓글 수정

    //3. 댓글 삭제
}
