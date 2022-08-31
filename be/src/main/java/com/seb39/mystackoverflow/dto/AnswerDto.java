package com.seb39.mystackoverflow.dto;

import com.seb39.mystackoverflow.entity.Answer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AnswerDto {

    @Getter
    @Setter
    public static class Post{
        private String content;
    }

    @Getter
    @Setter
    public static class Patch{
        private String content;
    }

    @Getter
    @Setter
    public static class Response{
        private Long questionId;
        private String questionTitle;
        private int votes;
        private boolean accepted;
        private LocalDateTime answeredTime;

        @Builder
        public Response(Long questionId, String questionTitle, int votes, boolean accepted, LocalDateTime answeredTime) {
            this.questionId = questionId;
            this.questionTitle = questionTitle;
            this.votes = votes;
            this.accepted = accepted;
            this.answeredTime = answeredTime;
        }
    }
}
