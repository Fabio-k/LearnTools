package com.LearnTools.LearnToolsApi.controller.dto;

import com.LearnTools.LearnToolsApi.model.entidades.MessagesEntity;

public class Messages {
    private String role;
    private String content;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static Messages fromEntity(MessagesEntity messagesEntity) {
        Messages messages = new Messages();
        messages.setRole(messagesEntity.getOrigin());
        messages.setContent(messagesEntity.getMessage());
        return messages;
    }
}
