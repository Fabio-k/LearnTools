package com.LearnTools.LearnToolsApi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.LearnTools.LearnToolsApi.controller.dto.Request.AiResumeRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.AiResumeResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Response.AiTagResponse;

@FeignClient(name = "aiClient", url = "http://localhost:11434/api")
public interface AiClient {

    @GetMapping("/tags")
    AiTagResponse getTags();

    @PostMapping("/chat")
    AiResumeResponse getResumeResponse(@RequestBody AiResumeRequest aiTitleRequest);
}
