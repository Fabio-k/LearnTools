package com.LearnTools.LearnToolsApi.controller.dto.Request;

import java.util.ArrayList;
import java.util.List;

public class ResumesRequest {
    private String title;
    private String description;
    private List<String> tagName = new ArrayList<>();

    public ResumesRequest() {
    }

    public ResumesRequest(String title, String description, List<String> tagName) {
        this.title = title;
        this.description = description;
        this.tagName = tagName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTagName() {
        return tagName;
    }
}
