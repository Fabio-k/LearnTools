package com.LearnTools.LearnToolsApi.controller.dto.Request;

public class ChatRequest {
    private Integer resumeId;
    private Integer assistentId;
    private String model;

    public Integer getResumeId() {
        return resumeId;
    }

    public void setResumeId(Integer resumeId) {
        this.resumeId = resumeId;
    }

    public Integer getAssistentId() {
        return assistentId;
    }

    public void setAssistentId(Integer assistentId) {
        this.assistentId = assistentId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}
