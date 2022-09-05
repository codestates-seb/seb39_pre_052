package com.seb39.mystackoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class MemberDto {

    @Getter
    public static class Response{
        private Long memberId;
        private String memberName;
        private LocalDateTime createdAt;

        @Builder
        public Response(Long memberId, String memberName, LocalDateTime createdAt) {
            this.memberId = memberId;
            this.memberName = memberName;
            this.createdAt = createdAt;
        }
    }



}
