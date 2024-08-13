package com.LearnTools.LearnToolsApi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.LearnTools.LearnToolsApi.client.AiClient;
import com.LearnTools.LearnToolsApi.controller.dto.Client.AiGenerateRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Client.AiGenerateResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Response.SimpleMessage;

@Service
public class AiService {
    private final AiClient aiClient;

    public AiService(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    public AiGenerateResponse getAiResponse(String model, List<SimpleMessage> messageList) {
        AiGenerateRequest aiGenerateRequest = new AiGenerateRequest();
        aiGenerateRequest.setModel(model);
        aiGenerateRequest.setMessages(messageList);
        aiGenerateRequest.setStream(false);

        AiGenerateResponse aiGenerateResponse = aiClient.getResumeResponse(aiGenerateRequest);

        return aiGenerateResponse;
    }
}
