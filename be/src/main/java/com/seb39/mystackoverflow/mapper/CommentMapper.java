package com.seb39.mystackoverflow.mapper;

import com.seb39.mystackoverflow.dto.CommentDto;
import com.seb39.mystackoverflow.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    Comment commentPostToComment(CommentDto.Post requestBody);

    default Comment commentPatchToComment(Long id, CommentDto.Patch requestBody){
        Comment comment = new Comment();
        comment.setId(id);
        comment.setContent(requestBody.getContent());
        return comment;
    }

    default CommentDto.Response commentToCommentResponse(Comment comment){

        return CommentDto.Response.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .lastModifiedAt(comment.getLastModifiedAt())
                .memberId(comment.getMember().getId())
                .postType(comment.getPostType())
                .postId(comment.getPostId())
                .build();

    }
}
