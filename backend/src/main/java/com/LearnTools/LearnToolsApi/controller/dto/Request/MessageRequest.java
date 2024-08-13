package com.LearnTools.LearnToolsApi.controller.dto.Request;

public class MessageRequest extends ChatRequest {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
