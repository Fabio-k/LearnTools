package com.LearnTools.LearnToolsApi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.Request.MessageRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.AiResumeResponse;
import com.LearnTools.LearnToolsApi.services.ChatService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping()
    public AiResumeResponse postChat(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody MessageRequest messageDTO,
            @RequestParam(required = false) Integer id) {

        if (id != null) {
            return chatService.handleAiChatReponse(userDetails, messageDTO, id);
        } else {
            return chatService.handleNewChat(userDetails, messageDTO);
        }
    }
}
