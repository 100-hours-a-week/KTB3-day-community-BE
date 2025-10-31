package com.demo.community.users.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class UsersResponseDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter @Setter
    public static class UserImageResponse{
        private String url;
        //private String key;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter @Setter
    public static class UserInfoResponse{
        private Long userId;
        private String email;
        private String nickname;
        private String userImage;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
