package com.seb39.mystackoverflow.mapper;

import com.seb39.mystackoverflow.dto.QuestionDto;
import com.seb39.mystackoverflow.entity.Question;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-25T18:53:30+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public Question questionPostToQuestion(QuestionDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Question question = new Question();

        question.setTitle( requestBody.getTitle() );
        question.setContent( requestBody.getContent() );

        return question;
    }

    @Override
    public Question questionPatchToQuestion(QuestionDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Question question = new Question();

        question.setId( requestBody.getId() );
        question.setTitle( requestBody.getTitle() );
        question.setContent( requestBody.getContent() );

        return question;
    }

    @Override
    public Question questionToQuestionResponse(Question question) {
        if ( question == null ) {
            return null;
        }

        Question question1 = new Question();

        question1.setCreatedAt( question.getCreatedAt() );
        question1.setLastModifiedAt( question.getLastModifiedAt() );
        question1.setId( question.getId() );
        question1.setTitle( question.getTitle() );
        question1.setContent( question.getContent() );
        question1.setView( question.getView() );
        question1.setVote( question.getVote() );

        return question1;
    }
}
