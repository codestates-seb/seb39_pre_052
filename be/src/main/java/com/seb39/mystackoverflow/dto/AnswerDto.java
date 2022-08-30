package com.seb39.mystackoverflow.dto;

import lombok.Getter;

public class AnswerDto {

    @Getter
    public static class Post{
        private String content;
    }

    @Getter
    public static class Patch{
        private String content;
    }
}
