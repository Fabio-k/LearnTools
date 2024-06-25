package com.LearnTools.LearnToolsApi.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.LearnTools.LearnToolsApi.model.entidades.Flashcard;

public class FlashcardResponseDTO {
    private String question;
    private String answer;
    private String username;
    private List<TagDTO> tags;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public static FlashcardResponseDTO fromEntity(Flashcard flashcard) {
        FlashcardResponseDTO dto = new FlashcardResponseDTO();
        dto.setQuestion(flashcard.getQuestion());
        dto.setAnswer(flashcard.getAnswer());
        dto.setUsername(flashcard.getUser().getUsername());
        dto.setTags(flashcard.getFlashCardTags().stream()
                .map(TagDTO::fromEntity).collect(Collectors.toList()));
        return dto;
    }
}