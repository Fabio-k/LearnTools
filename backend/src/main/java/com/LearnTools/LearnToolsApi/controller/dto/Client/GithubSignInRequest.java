package com.LearnTools.LearnToolsApi.controller.dto.Client;

public class GithubSignInRequest {
    private String code;
    private String clientId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}
