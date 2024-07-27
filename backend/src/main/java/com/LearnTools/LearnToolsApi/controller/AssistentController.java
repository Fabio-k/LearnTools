package com.LearnTools.LearnToolsApi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.Response.AssistentResponse;
import com.LearnTools.LearnToolsApi.services.AssistentService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/assistents")
public class AssistentController {
    private final AssistentService assistentService;

    public AssistentController(AssistentService assistentService) {
        this.assistentService = assistentService;
    }

    @GetMapping()
    public List<AssistentResponse> getAllAssistents() {
        return assistentService.getAllAssistents();
    }

}
