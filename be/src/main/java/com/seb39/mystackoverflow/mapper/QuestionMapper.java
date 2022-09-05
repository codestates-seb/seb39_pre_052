package com.seb39.mystackoverflow.mapper;

import com.seb39.mystackoverflow.dto.QuestionDetailDto;
import com.seb39.mystackoverflow.dto.QuestionDto;
import com.seb39.mystackoverflow.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {
    Question questionPostToQuestion(QuestionDto.Post requestBody);

    Question questionPatchToQuestion(QuestionDto.Patch requestBody);

    default QuestionDto.Response questionToQuestionResponse(Question question) {
        return QuestionDto.Response.builder()
                .id(question.getId())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .lastModifiedAt(question.getLastModifiedAt())
                .member(question.getMember())
                .title(question.getTitle())
                .view(question.getView())
                .vote(question.getVote())
                .answerNum(question.getAnswers().size())
                .build();
    }
    default List<QuestionDto.Response> questionsToQuestionResponses(List<Question> questions){
        return questions.stream()
                .map(this::questionToQuestionResponse)
                .collect(Collectors.toList());
    }
}
