package com.LearnTools.LearnToolsApi.controller.dto.Client;

import java.util.List;

import com.LearnTools.LearnToolsApi.controller.dto.Response.SimpleMessage;

public class AiResumeRequest {
    private String model;
    private List<SimpleMessage> messages;
    private Boolean stream;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<SimpleMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<SimpleMessage> messages) {
        this.messages = messages;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }
}
