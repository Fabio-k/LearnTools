package com.LearnTools.LearnToolsApi.controller.dto;

import java.time.LocalDateTime;

import com.LearnTools.LearnToolsApi.model.entidades.MessagesEntity;

public class MessageDto {
    private String message;
    private String role;
    private LocalDateTime time;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public static MessageDto fromEntity(MessagesEntity message) {
        MessageDto dto = new MessageDto();
        dto.setMessage(message.getMessage());
        dto.setRole(message.getOrigin());
        dto.setTime(message.getTimestamp());
        return dto;
    }
}
