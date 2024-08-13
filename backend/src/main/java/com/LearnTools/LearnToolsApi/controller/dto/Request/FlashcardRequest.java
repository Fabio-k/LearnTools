package com.LearnTools.LearnToolsApi.controller.dto.Request;

import java.util.ArrayList;
import java.util.List;

public class FlashcardRequest {
    private String question;
    private String answer;
    private List<String> tagsName = new ArrayList<>();

    public FlashcardRequest(String question, String answer, List<String> tagsName) {
        this.question = question;
        this.answer = answer;
        this.tagsName = tagsName;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getTagsName() {
        return tagsName;
    }
}
