package com.anurag.docvault.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDTOs {

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class SignupRequest {
        private String name;
        private String email;
        private String password;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class AuthResponse {
        private String token;
        private String role;
        private String email;
    }
}
