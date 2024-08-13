package com.LearnTools.LearnToolsApi.controller.dto.Response;

import java.util.List;
import java.util.stream.Collectors;

import com.LearnTools.LearnToolsApi.controller.dto.TagReqRes;
import com.LearnTools.LearnToolsApi.model.entidades.Flashcard;

public class FlashcardResponse {
    private String question;
    private String answer;
    private String username;
    private List<TagReqRes> tags;

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

    public List<TagReqRes> getTags() {
        return tags;
    }

    public void setTags(List<TagReqRes> tags) {
        this.tags = tags;
    }

    public static FlashcardResponse fromEntity(Flashcard flashcard) {
        FlashcardResponse dto = new FlashcardResponse();
        dto.setQuestion(flashcard.getQuestion());
        dto.setAnswer(flashcard.getAnswer());
        dto.setUsername(flashcard.getUser().getUsername());
        dto.setTags(flashcard.getFlashCardTags().stream()
                .map(TagReqRes::fromEntity).collect(Collectors.toList()));
        return dto;
    }
}