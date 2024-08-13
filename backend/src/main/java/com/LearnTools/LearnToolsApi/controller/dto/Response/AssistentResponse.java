package com.LearnTools.LearnToolsApi.controller.dto.Response;

import com.LearnTools.LearnToolsApi.model.entidades.Assistent;

public class AssistentResponse {
    private Integer id;
    private String name;
    private String prompt;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static AssistentResponse fromEntity(Assistent assistent) {
        AssistentResponse assistentResponse = new AssistentResponse();
        assistentResponse.setId(assistent.getId());
        assistentResponse.setName(assistent.getName());
        assistentResponse.setPrompt(assistent.getPrompt());
        assistentResponse.setDescription(assistent.getDescription());
        return assistentResponse;
    }

}
