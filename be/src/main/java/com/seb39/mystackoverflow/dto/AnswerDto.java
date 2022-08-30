package com.seb39.mystackoverflow.dto;

import lombok.Getter;
import lombok.Setter;

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
}
