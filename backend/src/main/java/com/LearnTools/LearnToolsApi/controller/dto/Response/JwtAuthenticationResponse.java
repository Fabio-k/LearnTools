package com.LearnTools.LearnToolsApi.controller.dto.Response;

import java.time.LocalDateTime;

public class JwtAuthenticationResponse {
    private String token;
    private LocalDateTime expirationDate;

    public JwtAuthenticationResponse() {
    }

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
