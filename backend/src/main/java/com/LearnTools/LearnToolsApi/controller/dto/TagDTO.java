package com.LearnTools.LearnToolsApi.controller.dto;

import com.LearnTools.LearnToolsApi.model.entidades.FlashCardTag;
import com.LearnTools.LearnToolsApi.model.entidades.Tag;

public class TagDTO {
    private String name;
    private String color;

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

    public static TagDTO fromEntity(FlashCardTag flashCardTag) {
        Tag tag = flashCardTag.getTag();
        TagDTO dto = new TagDTO();
        dto.setName(tag.getName());
        dto.setColor(tag.getColor());
        return dto;
    }

    public static TagDTO fromEntity(Tag tag) {
        TagDTO dto = new TagDTO();
        dto.setName(tag.getName());
        dto.setColor(tag.getColor());
        return dto;
    }
}
