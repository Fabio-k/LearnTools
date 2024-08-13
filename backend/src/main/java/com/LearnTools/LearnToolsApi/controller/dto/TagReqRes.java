package com.LearnTools.LearnToolsApi.controller.dto;

import com.LearnTools.LearnToolsApi.model.entidades.FlashCardTag;
import com.LearnTools.LearnToolsApi.model.entidades.ResumeTag;
import com.LearnTools.LearnToolsApi.model.entidades.Tag;

public class TagReqRes {
    private String name;
    private String color;

    public TagReqRes() {
    }

    public TagReqRes(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static TagReqRes createTag(Tag tag) {
        TagReqRes dto = new TagReqRes();
        dto.setName(tag.getName());
        dto.setColor(tag.getColor());
        return dto;
    }

    public static TagReqRes fromEntity(FlashCardTag flashCardTag) {
        return createTag(flashCardTag.getTag());
    }

    public static TagReqRes fromEntity(ResumeTag resumeTag) {
        return createTag(resumeTag.getTag());
    }

    public static TagReqRes fromEntity(Tag tag) {
        return createTag(tag);
    }

}
