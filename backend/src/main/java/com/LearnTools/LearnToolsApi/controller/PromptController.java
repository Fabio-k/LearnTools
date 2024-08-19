package com.LearnTools.LearnToolsApi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.AssistentDto;
import com.LearnTools.LearnToolsApi.services.PromptService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/assistents")
public class PromptController {
    private final PromptService assistentService;

    public PromptController(PromptService assistentService) {
        this.assistentService = assistentService;
    }

    @GetMapping()
    public List<AssistentDto> getAllAssistents() {
        return assistentService.getAllPrompts();
    }

    @PatchMapping()
    public ResponseEntity<AssistentDto> patchAssistent(@RequestBody AssistentDto req) {
        return ResponseEntity.ok(assistentService.patchPrompt(req));
    }

}
