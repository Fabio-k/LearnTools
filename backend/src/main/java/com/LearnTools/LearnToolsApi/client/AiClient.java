package com.LearnTools.LearnToolsApi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.LearnTools.LearnToolsApi.controller.dto.AiTagResponse;
import com.LearnTools.LearnToolsApi.controller.dto.AiResumeRequest;
import com.LearnTools.LearnToolsApi.controller.dto.AiResumeResponse;

@FeignClient(name = "aiClient", url = "http://localhost:11434/api")
public interface AiClient {

    @GetMapping("/tags")
    AiTagResponse getTags();

    @PostMapping("/chat")
    AiResumeResponse getResumeResponse(@RequestBody AiResumeRequest aiTitleRequest);
}
