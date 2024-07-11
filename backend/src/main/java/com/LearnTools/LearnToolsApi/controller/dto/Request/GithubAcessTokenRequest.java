package com.LearnTools.LearnToolsApi.controller.dto.Request;

public class GithubAcessTokenRequest {
    private String code;
    private String client_secret;
    private String client_id;

    public GithubAcessTokenRequest(String code, String client_secret, String client_id) {
        this.code = code;
        this.client_secret = client_secret;
        this.client_id = client_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

}
