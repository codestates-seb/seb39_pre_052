package com.seb39.mystackoverflow.test;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {

        String testKeyword = "spring boot user:123415 asdasdad";

        if (Arrays.stream(testKeyword.split(" ")).anyMatch(s -> s.startsWith("user:"))) {
            System.out.println("if문 동작");
            StringBuilder sb = new StringBuilder();
            testKeyword = testKeyword.substring(testKeyword.indexOf(":") + 1);
            for (String s : testKeyword.split("")) {
                if(s != null && s.matches("[0-9]+")){
                    sb.append(s);
                }
            }
            System.out.println(Long.parseLong(sb.toString()));

        }
    }
}
