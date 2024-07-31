package com.LearnTools.LearnToolsApi.controller.dto.Client;

import com.LearnTools.LearnToolsApi.controller.dto.Response.SimpleMessage;

public class AiResumeResponse {
    private SimpleMessage message;
    private Integer chatId;

    public SimpleMessage getMessage() {
        return message;
    }

    public void setMessage(SimpleMessage message) {
        this.message = message;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }
}