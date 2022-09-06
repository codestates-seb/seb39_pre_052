package com.seb39.mystackoverflow.dto;

import lombok.Builder;
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

    @Builder
    public QuestionDetailDto(Long id, String title, String content, LocalDateTime askedAt, int view, int vote, Member member, List<Comment> comments, List<Answer> answers) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.askedAt = askedAt;
        this.view = view;
        this.vote = vote;
        this.member = member;
        this.comments = comments;
        this.answers = answers;
    }

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
        private int vote;
        private boolean accepted;
        private Member member;
        private List<Comment> comments;

        @Builder
        public Answer(Long id, String content, LocalDateTime answeredAt, int vote, boolean accepted, Member member, List<Comment> comments) {
            this.id = id;
            this.content = content;
            this.answeredAt = answeredAt;
            this.vote = vote;
            this.accepted = accepted;
            this.member = member;
            this.comments = comments;
        }
    }

    @Getter
    @Setter
    public static class Comment {
        private Long id;
        private String content;
        private LocalDateTime createdAt;
        private Member member;

        @Builder
        public Comment(Long id, String content, LocalDateTime createdAt, Member member) {
            this.id = id;
            this.content = content;
            this.createdAt = createdAt;
            this.member = member;
        }
    }
}