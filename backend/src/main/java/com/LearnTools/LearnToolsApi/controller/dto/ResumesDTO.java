package com.LearnTools.LearnToolsApi.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class ResumesDTO {
    private String title;
    private String resume;
    private List<String> tagName = new ArrayList<>();

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
