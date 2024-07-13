package com.LearnTools.LearnToolsApi.services;

import org.springframework.stereotype.Service;

import com.LearnTools.LearnToolsApi.controller.dto.Response.AnaliticsResponse;

@Service
public class AnaliticsService {
    private final FlashcardService flashcardService;
    private final TagService tagService;

    public AnaliticsService(FlashcardService flashcardService, TagService tagService) {
        this.flashcardService = flashcardService;
        this.tagService = tagService;
    }

    public AnaliticsResponse getAnalitics(String username) {
        AnaliticsResponse analiticsResponse = flashcardService.getAnalitics(username);
        analiticsResponse.setTags(tagService.getTagsDto(username));
        return analiticsResponse;
    }
}
