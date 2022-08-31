package com.seb39.mystackoverflow.mapper;

import com.seb39.mystackoverflow.dto.CommentDto;
import com.seb39.mystackoverflow.entity.Comment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-31T18:18:43+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment commentPostToComment(CommentDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setContent( requestBody.getContent() );

        return comment;
    }

    @Override
    public Comment commentPatchToComment(CommentDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( requestBody.getId() );
        comment.setContent( requestBody.getContent() );

        return comment;
    }
}
