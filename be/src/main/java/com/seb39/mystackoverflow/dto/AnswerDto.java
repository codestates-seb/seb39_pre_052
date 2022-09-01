package com.seb39.mystackoverflow.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
        private Long id;
        private int vote;
        private boolean accepted;
        private LocalDateTime answeredTime;
        private Question question;

        @Builder
        public Response(Long id, int vote, boolean accepted, LocalDateTime answeredTime, Long questionId, String questionTitle) {
            this.id = id;
            this.vote = vote;
            this.accepted = accepted;
            this.answeredTime = answeredTime;
            this.question = new Question(questionId,questionTitle);
        }

        @Getter
        private static class Question{
            Long id;
            String title;

            public Question(Long id, String title) {
                this.id = id;
                this.title = title;
            }
        }
    }
}
