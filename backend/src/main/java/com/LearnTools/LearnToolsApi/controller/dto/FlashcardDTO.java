package com.LearnTools.LearnToolsApi.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class FlashcardDTO {
    private String question;
    private String answer;
    private List<String> tagsName = new ArrayList<>();

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