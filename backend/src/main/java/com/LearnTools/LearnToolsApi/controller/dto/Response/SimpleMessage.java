package com.LearnTools.LearnToolsApi.controller.dto.Response;

import com.LearnTools.LearnToolsApi.controller.dto.Message;
import com.LearnTools.LearnToolsApi.model.entidades.MessagesEntity;

public class SimpleMessage extends Message {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static SimpleMessage fromEntity(MessagesEntity messagesEntity) {
        SimpleMessage messages = new SimpleMessage();
        messages.setRole(messagesEntity.getOrigin());
        messages.setContent(messagesEntity.getMessage());
        return messages;
    }
}
