package com.seb39.mystackoverflow.dto;

import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    public static class Post {

        private long questionId;

        @NotBlank
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class Response{
        private Long id;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime lastModifiedAt;
        private Member member;
        private Question question;

        public Long getMember() {
            return member.getId();
        }
        public Long getQuestion() {
            return question.getId();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        public Patch() {
        }

        private long id;

        @NotBlank(message = "수정할 댓글 내용은 공백이 아니어야 합니다.")
        private String content;

        public void setId(long id) {
            this.id = id;
        }

    }
}
