package com.LearnTools.LearnToolsApi.controller.dto.Response;

import java.util.Date;

public class UserResponse {
    private String username;
    private Date createdAt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
