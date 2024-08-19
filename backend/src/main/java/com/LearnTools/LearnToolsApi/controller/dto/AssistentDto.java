package com.LearnTools.LearnToolsApi.controller.dto;

import com.LearnTools.LearnToolsApi.model.entidades.Assistent;

public class AssistentDto {
    private Integer id;
    private String name;
    private String prompt;
    private String description;

    public AssistentDto() {
    }

    public AssistentDto(Integer id, String name, String prompt, String description) {
        this.id = id;
        this.name = name;
        this.prompt = prompt;
        this.description = description;
    }

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

    public static AssistentDto fromEntity(Assistent assistent) {
        AssistentDto assistentResponse = new AssistentDto();
        assistentResponse.setId(assistent.getId());
        assistentResponse.setName(assistent.getPrompt().getName());
        assistentResponse.setPrompt(assistent.getPrompt().getPrompt());
        assistentResponse.setDescription(assistent.getDescription());
        return assistentResponse;
    }

}
