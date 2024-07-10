package com.LearnTools.LearnToolsApi.controller.dto.Request;

public class MessageRequest {
    private Integer resumeID;
    private String message;
    private Integer assistentID;
    private String model;

    public void setResumeID(Integer resumeID) {
        this.resumeID = resumeID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAssistentID(Integer assistentID) {
        this.assistentID = assistentID;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMessage() {
        return message;
    }

    public Integer getResumeID() {
        return resumeID;
    }

    public Integer getAssistentID() {
        return assistentID;
    }

    public String getModel() {
        return model;
    }
}
