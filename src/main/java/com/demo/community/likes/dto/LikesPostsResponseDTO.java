package com.demo.community.likes.dto;

import lombok.*;

public class LikesPostsResponseDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class LikesPostsResultResponse {
        private Boolean userPressed;
        private Integer likeCount;
    }

}
