package com.LearnTools.LearnToolsApi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.TagDTO;
import com.LearnTools.LearnToolsApi.handler.CampoObrigatorioException;
import com.LearnTools.LearnToolsApi.model.entidades.Tag;
import com.LearnTools.LearnToolsApi.model.repository.TagRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController

public class TagController {

    @Autowired
    private TagRepository repository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/tags")
    public List<TagDTO> getTags(@AuthenticationPrincipal UserDetails userDetails) {
        List<Tag> tags = repository.findAllByUserUsername(userDetails.getUsername());
        return tags.stream().map(TagDTO::fromEntity).collect(Collectors.toList());
    }

    @PostMapping("/tags")
    public void postMethodName(@RequestBody TagDTO tagDTO, @AuthenticationPrincipal UserDetails userDetails) {
        if (tagDTO.getName() == null)
            throw new CampoObrigatorioException("nome");
        if (tagDTO.getColor() == null)
            throw new CampoObrigatorioException("cor");
        Tag tag = new Tag(tagDTO.getName(), tagDTO.getColor());
        tag.setUser(userRepository.findByUsername(userDetails.getUsername()));
        repository.save(tag);
    }

}
