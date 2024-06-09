package com.LearnTools.LearnToolsApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.TagDTO;
import com.LearnTools.LearnToolsApi.handler.CampoObrigatorioException;
import com.LearnTools.LearnToolsApi.model.entidades.Tag;
import com.LearnTools.LearnToolsApi.model.repository.TagRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class TagController {

    @Autowired
    TagRepository repository;

    @GetMapping("/tags")
    public List<Tag> getTags() {
        return repository.findAll();
    }

    @PostMapping("/tags")
    public void postMethodName(@RequestBody TagDTO tagDTO) {
        if (tagDTO.getName() == null)
            throw new CampoObrigatorioException("nome");
        if (tagDTO.getColor() == null)
            throw new CampoObrigatorioException("cor");
        Tag tag = new Tag(tagDTO.getName(), tagDTO.getColor());
        repository.save(tag);
    }

}
