package com.LearnTools.LearnToolsApi.controller.dto.Request;

import java.util.List;

import com.LearnTools.LearnToolsApi.controller.dto.Response.MessagesResponse;

public class AiResumeRequest {
    private String model;
    private List<MessagesResponse> messages;
    private Boolean stream;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<MessagesResponse> getMessages() {
        return messages;
    }

    public void setMessages(List<MessagesResponse> messages) {
        this.messages = messages;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }
}
