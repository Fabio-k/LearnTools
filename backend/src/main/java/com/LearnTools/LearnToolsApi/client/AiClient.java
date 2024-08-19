package com.LearnTools.LearnToolsApi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.LearnTools.LearnToolsApi.controller.dto.Client.AiGenerateRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Client.AiGenerateResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Response.AiTagResponse;

@FeignClient(name = "aiClient", url = "${ollama.url}")
public interface AiClient {

    @GetMapping("/tags")
    AiTagResponse getTags();

    @PostMapping("/chat")
    AiGenerateResponse getResumeResponse(@RequestBody AiGenerateRequest aiTitleRequest);
}
