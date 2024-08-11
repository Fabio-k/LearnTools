package com.LearnTools.LearnToolsApi.controller.dto.Response;

import com.LearnTools.LearnToolsApi.controller.dto.MessageDto;

public class AiMessageResponse {
    private MessageDto message;
    private Integer chatId;

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public MessageDto getMessage() {
        return message;
    }

    public void setMessage(MessageDto message) {
        this.message = message;
    }

}
