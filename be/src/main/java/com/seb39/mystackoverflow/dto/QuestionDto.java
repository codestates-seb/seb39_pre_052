package com.seb39.mystackoverflow.dto;

import com.seb39.mystackoverflow.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class QuestionDto {

    //Post Dto
    @Getter
    public static class Post {

        //질문 제목 NotBlank
        @NotBlank(message = "질문 제목은 공백이 아니어야 합니다.")
        private String title;

        //질문 내용 NotBlank
        @NotBlank(message = "질문 내용은 공백이 아니어야 합니다.")
        private String content;
        
    }

    //Patch Dto
    @Getter
    @AllArgsConstructor
    public static class Patch {
        public Patch() {
        }

        private long id;

        //질문 제목 NotBlank
        @NotBlank(message = "수정할 질문 제목은 공백이 아니어야 합니다.")
        private String title;

        //질문 내용 NotBlank
        @NotBlank(message = "수정할 질문 내용은 공백이 아니어야 합니다.")
        private String content;

        public void setId(long id) {
            this.id = id;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class response{
        private Long id;
        private String title;
        private String content;
        private int view;
        private int vote;
        private LocalDateTime createdAt;
        private LocalDateTime lastModifiedAt;
    }

}
