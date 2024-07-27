package com.LearnTools.LearnToolsApi.controller.dto.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import com.LearnTools.LearnToolsApi.controller.dto.MessageDto;

public class ChatResponse {
    Map<Integer, TreeSet<MessageDto>> chats = new HashMap<>();

    public Map<Integer, TreeSet<MessageDto>> getChats() {
        return chats;
    }

    public void setChats(Map<Integer, TreeSet<MessageDto>> chats) {
        this.chats = chats;
    }
}
