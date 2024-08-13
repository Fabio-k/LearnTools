package com.LearnTools.LearnToolsApi.controller.dto.Client;

import com.LearnTools.LearnToolsApi.controller.dto.Response.SimpleMessage;

public class AiGenerateResponse {
    private SimpleMessage message;

    public SimpleMessage getMessage() {
        return message;
    }

    public void setMessage(SimpleMessage message) {
        this.message = message;
    }
}