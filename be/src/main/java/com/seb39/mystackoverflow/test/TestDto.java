package com.seb39.mystackoverflow.test;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TestDto {

    private String title;

    private String content;

    private int vote;

    private int view;

    private String name; //member name

    private int answered;

    private LocalDateTime createdAt;


    @Builder
    public TestDto(String title, String content, int vote, int view, String name, int answered, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.vote = vote;
        this.view = view;
        this.name = name;
        this.answered = answered;
        this.createdAt = createdAt;
    }
}
