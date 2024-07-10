package com.LearnTools.LearnToolsApi.controller.dto.Response;

import com.LearnTools.LearnToolsApi.model.entidades.MessagesEntity;

public class MessagesResponse {
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

    public static MessagesResponse fromEntity(MessagesEntity messagesEntity) {
        MessagesResponse messages = new MessagesResponse();
        messages.setRole(messagesEntity.getOrigin());
        messages.setContent(messagesEntity.getMessage());
        return messages;
    }
}
