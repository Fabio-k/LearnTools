package com.LearnTools.LearnToolsApi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.client.AiClient;
import com.LearnTools.LearnToolsApi.controller.dto.Request.MessageRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.AiResumeResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Response.AiTagResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Response.ChatResponse;
import com.LearnTools.LearnToolsApi.services.ChatService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final AiClient aiClient;

    public ChatController(ChatService chatService, AiClient aiClient) {
        this.chatService = chatService;
        this.aiClient = aiClient;
    }

    @GetMapping()
    public ResponseEntity<ChatResponse> getChats(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(chatService.getAllChats(userDetails.getUsername()));
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

    @GetMapping("/ai")
    public AiTagResponse getAllAiTags() {
        return aiClient.getTags();
    }
}
