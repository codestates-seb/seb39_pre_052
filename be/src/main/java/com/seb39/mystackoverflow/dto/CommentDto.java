package com.seb39.mystackoverflow.dto;

import com.seb39.mystackoverflow.entity.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    @Setter
    public static class Post {

        @NotBlank
        private String content;
    }


    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime lastModifiedAt;
        private Long memberId;
        private PostInfo postInfo;

        @Builder
        public Response(Long id, String content, LocalDateTime createdAt, LocalDateTime lastModifiedAt, Long memberId, PostType postType, Long postId) {
            this.id = id;
            this.content = content;
            this.createdAt = createdAt;
            this.lastModifiedAt = lastModifiedAt;
            this.memberId = memberId;
            this.postInfo = new PostInfo(postType, postId);
        }

        @Getter
        static class PostInfo {
            private PostType postType;
            private Long postId;

            public PostInfo(PostType postType, Long postId) {
                this.postType = postType;
                this.postId = postId;
            }
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        public Patch() {
        }

        // private long id;

        @NotBlank(message = "수정할 댓글 내용은 공백이 아니어야 합니다.")
        private String content;

//        public void setId(long id) {
//            this.id = id;
//        }

    }
}
