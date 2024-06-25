package com.LearnTools.LearnToolsApi.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class ResumesDTO {
    private String title;
    private String resume;
    private List<String> tagName = new ArrayList<>();

    public ResumesDTO() {
    }

    public ResumesDTO(String title, String resume, List<String> tagName) {
        this.title = title;
        this.resume = resume;
        this.tagName = tagName;
    }

    public String getTitle() {
        return title;
    }

    public String getResume() {
        return resume;
    }

    public List<String> getTagName() {
        return tagName;
    }
}
