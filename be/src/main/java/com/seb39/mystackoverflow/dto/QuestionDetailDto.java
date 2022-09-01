package com.seb39.mystackoverflow.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class QuestionDetailDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime askedAt;
    private int view;
    private int vote;
    private Member member;
    private List<Comment> comments;
    private List<Answer> answers;

    @Getter
    @Setter
    public static class Member {
        private Long id;
        private String name;

        public Member(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Getter
    @Setter
    public static class Answer {
        private Long id;
        private String content;
        private LocalDateTime answeredAt;
        private Member member;
        private List<Comment> comments;
    }

    @Getter
    @Setter
    public static class Comment {
        private Long id;
        private String content;
        private Member member;

        public Comment(Long id, String content, Member member) {
            this.id = id;
            this.content = content;
            this.member = member;
        }
    }
}