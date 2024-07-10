package com.LearnTools.LearnToolsApi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.LearnTools.LearnToolsApi.controller.dto.TagReqRes;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.handler.CampoObrigatorioException;
import com.LearnTools.LearnToolsApi.model.entidades.Tag;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.repository.TagRepository;

@Service
public class TagService {
    private final TagRepository tagRepository;

    private final UserService userService;

    public TagService(TagRepository tagRepository, UserService userService) {
        this.tagRepository = tagRepository;
        this.userService = userService;
    }

    public List<Tag> getUserTags(String username) {
        List<Tag> tags = tagRepository.findAllByUserUsername(username);
        return tags;
    }

    public List<TagReqRes> getTagsDto(String username) {
        List<Tag> tags = getUserTags(username);
        return tags.stream().map(TagReqRes::fromEntity).collect(Collectors.toList());
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

    public Tag createTag(TagReqRes tagDTO, String username) {
        if (tagDTO.getName() == null)
            throw new CampoObrigatorioException("nome");
        if (tagDTO.getColor() == null)
            throw new CampoObrigatorioException("cor");

        Tag tag = new Tag(tagDTO.getName(), tagDTO.getColor());

        User user = userService.getUser(username);
        tag.setUser(user);
        return tagRepository.save(tag);
    }
}
