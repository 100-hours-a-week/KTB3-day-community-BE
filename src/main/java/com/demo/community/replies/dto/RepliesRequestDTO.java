package com.demo.community.replies.dto;

import lombok.*;

import java.time.LocalDateTime;

public class RepliesRequestDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ReplyCreateRequest{
        private Long postId;
        private String content;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ReplyUpdateRequest{
        private String content;
    }

}
