package com.seb39.mystackoverflow.mapper;

import com.seb39.mystackoverflow.dto.QuestionDetailDto;
import com.seb39.mystackoverflow.entity.Answer;
import com.seb39.mystackoverflow.entity.Comment;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionDetailMapper {

    default QuestionDetailDto questionToQuestionDetail(Question question){
        return QuestionDetailDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .askedAt(question.getCreatedAt())
                .view(question.getView())
                .vote(question.getVote())
                .member(memberToDto(question.getMember()))
                .comments(commentsToDtos(question.getComments()))
                .answers(answersToDtos(question.getAnswers()))
                .build();
    }

    default QuestionDetailDto.Member memberToDto(Member member){
        return new QuestionDetailDto.Member(member.getId(),member.getName());
    }

    default List<QuestionDetailDto.Answer> answersToDtos(List<Answer> answers){
        return answers.stream()
                .map(this::answerToDto)
                .collect(Collectors.toList());
    }

    default QuestionDetailDto.Answer answerToDto(Answer answer){
        return QuestionDetailDto.Answer.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .answeredAt(answer.getCreatedAt())
                .vote(answer.getVote())
                .accepted(answer.isAccepted())
                .member(memberToDto(answer.getMember()))
                .comments(commentsToDtos(answer.getComments()))
                .build();
    }

    default List<QuestionDetailDto.Comment> commentsToDtos(List<Comment> comments){
        return comments.stream()
                .map(this::commentToDto)
                .collect(Collectors.toList());
    }

    default QuestionDetailDto.Comment commentToDto(Comment comment){
        return QuestionDetailDto.Comment.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .member(memberToDto(comment.getMember()))
                .build();
    }
}
