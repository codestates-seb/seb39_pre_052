package com.seb39.mystackoverflow.dto;

import com.seb39.mystackoverflow.entity.Answer;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class QuestionDto {

    //Post Dto
    @Getter
    @Setter
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
    @Setter
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
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private int view;
        private int vote;
        private LocalDateTime createdAt;
        private LocalDateTime lastModifiedAt;
        //        private Member member;
        private MemberSimple member;
        private int answerNum;

        @Builder
        public Response(Long id, String title, String content, int view, int vote, LocalDateTime createdAt, LocalDateTime lastModifiedAt, Member member,int answerNum) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.view = view;
            this.vote = vote;
            this.createdAt = createdAt;
            this.lastModifiedAt = lastModifiedAt;
            this.member = new MemberSimple(member.getId(), member.getName());
            this.answerNum = answerNum;
        }

        @Getter
        public static class MemberSimple {
            private Long memberId;
            private String memberName;
            public MemberSimple(Long memberId, String memberName) {
                this.memberId = memberId;
                this.memberName = memberName;
            }
        }
    }

}
