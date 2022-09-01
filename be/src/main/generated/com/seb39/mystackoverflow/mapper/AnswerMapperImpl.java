package com.seb39.mystackoverflow.mapper;

import com.seb39.mystackoverflow.dto.AnswerDto;
import com.seb39.mystackoverflow.entity.Answer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-01T17:22:21+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class AnswerMapperImpl implements AnswerMapper {

    @Override
    public Answer answerPostToAnswer(AnswerDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Answer.AnswerBuilder answer = Answer.builder();

        answer.content( requestBody.getContent() );

        return answer.build();
    }
}
