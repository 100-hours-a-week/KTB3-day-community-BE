package com.demo.community.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

public class AuthRequestDTO {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class LoginRequest{
        @NotNull
        private String email;
        @NotNull
        private String password;
    }
}
