package com.LearnTools.LearnToolsApi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.TagReqRes;
import com.LearnTools.LearnToolsApi.model.entidades.Tag;
import com.LearnTools.LearnToolsApi.services.TagService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @CrossOrigin
    @GetMapping()
    public List<TagReqRes> getTags(@AuthenticationPrincipal UserDetails userDetails) {
        return tagService.getTagsDto(userDetails.getUsername());
    }

    @DeleteMapping("/{tagName}")
    public void deleteTag(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String tagName) {
        tagService.deleteTag(userDetails.getUsername(), tagName);
    }

    @CrossOrigin
    @PostMapping()
    public ResponseEntity<Tag> postTag(@RequestBody TagReqRes tagDTO,
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(tagService.createTag(tagDTO, userDetails.getUsername()));
    }

}
