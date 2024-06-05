package com.LearnTools.LearnToolsApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.model.Tag;
import com.LearnTools.LearnToolsApi.repository.TagRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class tagController {

    @Autowired
    TagRepository repository;

    @GetMapping("/tags")
    public List<Tag> getTags() {
        return repository.findAll();
    }

    @PostMapping("/tags")
    public void postMethodName(@RequestBody TagDTO tagDTO) {
        Tag tag = new Tag(tagDTO.getName(), tagDTO.getColor());
        repository.save(tag);
    }

}

class TagDTO {
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

}