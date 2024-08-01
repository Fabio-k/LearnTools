package com.LearnTools.LearnToolsApi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.client.AiClient;
import com.LearnTools.LearnToolsApi.controller.dto.Request.MessageRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Client.AiResumeResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Request.ChatRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.AiTagResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Response.ChatResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Response.ChatStatusResponse;
import com.LearnTools.LearnToolsApi.services.ChatService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final AiClient aiClient;

    public ChatController(ChatService chatService, AiClient aiClient) {
        this.chatService = chatService;
        this.aiClient = aiClient;
    }

    @PostMapping("status/{resumeId}")
    public ResponseEntity<ChatStatusResponse> getStatus(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer resumeId) {
        return ResponseEntity.ok(chatService.getStatus(userDetails.getUsername(), resumeId));
    }

    @PostMapping()
    public ResponseEntity<ChatResponse> getChats(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ChatRequest request) {
        return ResponseEntity.ok(chatService.getOrCreateChat(userDetails.getUsername(), request));
    }

    @PostMapping("/{id}")
    public AiResumeResponse postChat(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody MessageRequest messageDTO,
            @PathVariable Integer id) {

        return chatService.handleAiChatReponse(userDetails, messageDTO, id);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<String> deleteChat(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer chatId) {
        chatService.deleteChat(userDetails.getUsername(), chatId);
        return ResponseEntity.ok("deleted sucessfully");
    }

    @GetMapping("/tags")
    public AiTagResponse getAllAiTags() {
        return aiClient.getTags();
    }
}
