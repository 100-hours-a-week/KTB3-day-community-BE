package com.demo.community.replies.dto;

import com.demo.community.posts.dto.PostResponseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class RepliesResponseDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ReplyDetailResponse{
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String nickname;
        private String profileImg;
        private Long id;
        private Long userId;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ReplyListSliceResponse {
        private List<RepliesResponseDTO.ReplyDetailResponse> items;
        private boolean hasNext;
        private Long nextCursorId;
    }

}
