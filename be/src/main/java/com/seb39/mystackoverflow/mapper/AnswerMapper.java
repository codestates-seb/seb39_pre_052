package com.seb39.mystackoverflow.mapper;

import com.seb39.mystackoverflow.dto.AnswerDto;
import com.seb39.mystackoverflow.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {
    Answer answerPostToAnswer(AnswerDto.Post requestBody);
    default Answer answerPatchToAnswer(Long answerId,AnswerDto.Patch requestBody){
        Answer answer = new Answer();
        answer.setId(answerId);
        answer.setContent(requestBody.getContent());
        return answer;
    }

    default AnswerDto.Response answerToAnswerResponse(Answer answer){
        return AnswerDto.Response.builder()
                .questionId(answer.getQuestion().getId())
                .questionTitle(answer.getQuestion().getTitle())
                .votes(answer.getVote())
                .accepted(answer.isAccepted())
                .answeredTime(answer.getLastModifiedAt())
                .build();
    }
}
