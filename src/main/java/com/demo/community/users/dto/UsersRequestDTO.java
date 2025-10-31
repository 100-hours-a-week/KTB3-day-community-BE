package com.demo.community.users.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

public class UsersRequestDTO {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class UserCreateRequest{
        @NotNull
        private String email;
        @NotNull
        private String password;
        @NotNull
        private String nickname;
        @NotNull
        private String profileImage;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class EmailCheckRequest{
        @NotNull
        private String email;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class NicknameCheckRequest{
        @NotNull
        private String nickname;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class UserUpdateRequest{
        private String nickname;
        private String profileImage;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class PasswordUpdateRequest{
        private String curPassword;
        private String newPassword;
    }

}
