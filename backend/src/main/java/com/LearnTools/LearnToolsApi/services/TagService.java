package com.LearnTools.LearnToolsApi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.LearnTools.LearnToolsApi.controller.dto.TagDTO;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.handler.CampoObrigatorioException;
import com.LearnTools.LearnToolsApi.model.entidades.Tag;
import com.LearnTools.LearnToolsApi.model.repository.TagRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public TagService(TagRepository tagRepository, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    public List<Tag> getUserTags(String username) {
        List<Tag> tags = tagRepository.findAllByUserUsername(username);
        return tags;
    }

    public List<TagDTO> getTagsDto(String username) {
        List<Tag> tags = getUserTags(username);
        return tags.stream().map(TagDTO::fromEntity).collect(Collectors.toList());
    }

    public void deleteTag(String username, String tagName) {
        List<Tag> tags = getUserTags(username);
        Optional<Tag> selectedTag = tags.stream().filter(t -> t.getName() == tagName).findFirst();
        if (selectedTag.isEmpty())
            throw new BusinessException("tag not found");
        tagRepository.delete(selectedTag.get());
    }

    public void deleteTag(String username, Integer tagId) {
        List<Tag> tags = getUserTags(username);
        Optional<Tag> selectedTag = tags.stream().filter(t -> t.getId() == tagId).findFirst();
        if (selectedTag.isEmpty())
            throw new BusinessException("tag not found");
        tagRepository.delete(selectedTag.get());
    }

    public Tag createTag(TagDTO tagDTO, String username) {
        if (tagDTO.getName() == null)
            throw new CampoObrigatorioException("nome");
        if (tagDTO.getColor() == null)
            throw new CampoObrigatorioException("cor");

        Tag tag = new Tag(tagDTO.getName(), tagDTO.getColor());
        tag.setUser(userRepository.findByUsername(username));
        return tagRepository.save(tag);
    }
}
