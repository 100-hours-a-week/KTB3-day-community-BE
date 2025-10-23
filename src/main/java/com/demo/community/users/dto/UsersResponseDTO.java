package com.demo.community.users.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

public class UsersResponseDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter @Setter
    public static class UserImageResponse{
        private String url;
        //private String key;
    }
}
