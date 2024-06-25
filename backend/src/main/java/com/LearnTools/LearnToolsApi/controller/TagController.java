package com.LearnTools.LearnToolsApi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.TagDTO;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.handler.CampoObrigatorioException;
import com.LearnTools.LearnToolsApi.model.entidades.Tag;
import com.LearnTools.LearnToolsApi.model.repository.TagRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagRepository repository;
    private final UserRepository userRepository;

    public TagController(TagRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public List<TagDTO> getTags(@AuthenticationPrincipal UserDetails userDetails) {
        List<Tag> tags = repository.findAllByUserUsername(userDetails.getUsername());
        return tags.stream().map(TagDTO::fromEntity).collect(Collectors.toList());
    }

    @DeleteMapping("/{tagName}")
    public void deleteTag(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String tagName) {
        List<Tag> tags = repository.findAllByUserUsername(userDetails.getUsername());
        Optional<Tag> selectedTag = tags.stream().filter(t -> t.getName() == tagName).findFirst();
        if (selectedTag.isEmpty())
            throw new BusinessException("tag not found");
        repository.delete(selectedTag.get());
    }

    @PostMapping()
    public ResponseEntity<Integer> postMethodName(@RequestBody TagDTO tagDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (tagDTO.getName() == null)
            throw new CampoObrigatorioException("nome");
        if (tagDTO.getColor() == null)
            throw new CampoObrigatorioException("cor");

        Tag tag = new Tag(tagDTO.getName(), tagDTO.getColor());
        tag.setUser(userRepository.findByUsername(userDetails.getUsername()));
        repository.save(tag);

        return ResponseEntity.ok(tag.getId());
    }

}
