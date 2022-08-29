package com.seb39.mystackoverflow.mapper;

import com.seb39.mystackoverflow.dto.QuestionDto;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-27T20:26:12+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
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
    public QuestionDto.response questionToQuestionResponse(Question question) {
        if ( question == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String content = null;
        int view = 0;
        int vote = 0;
        LocalDateTime createdAt = null;
        LocalDateTime lastModifiedAt = null;
        Member member = null;

        id = question.getId();
        title = question.getTitle();
        content = question.getContent();
        view = question.getView();
        vote = question.getVote();
        createdAt = question.getCreatedAt();
        lastModifiedAt = question.getLastModifiedAt();
        member = question.getMember();

        QuestionDto.response response = new QuestionDto.response( id, title, content, view, vote, createdAt, lastModifiedAt, member );

        return response;
    }
}
