package com.LearnTools.LearnToolsApi.controller.dto.Response;

import java.util.TreeSet;

import com.LearnTools.LearnToolsApi.controller.dto.MessageDto;

public class ChatResponse {
    private TreeSet<MessageDto> messages;
    private Integer chatId;
    private String assistentName;
    private Boolean isNewChat;

    public TreeSet<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(TreeSet<MessageDto> messages) {
        this.messages = messages;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getAssistentName() {
        return assistentName;
    }

    public void setAssistentName(String assistentName) {
        this.assistentName = assistentName;
    }

    public Boolean getIsNewChat() {
        return isNewChat;
    }

    public void setIsNewChat(Boolean isNewChat) {
        this.isNewChat = isNewChat;
    }

}
