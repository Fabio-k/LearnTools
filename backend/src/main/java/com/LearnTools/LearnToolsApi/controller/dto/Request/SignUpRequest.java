package com.LearnTools.LearnToolsApi.controller.dto.Request;

public class SignUpRequest {
    private String name;
    private String username;
    private String password;

    public SignUpRequest() {
    }

    public SignUpRequest(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
