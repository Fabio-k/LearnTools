package com.LearnTools.LearnToolsApi.controller.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.LearnTools.LearnToolsApi.model.entidades.Resume;

public class ResumesResponseDTO {

    private String title;
    private String resume;
    private List<TagDTO> tags = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTagsResponse(List<TagDTO> tags) {
        this.tags = tags;
    }

    public static ResumesResponseDTO fromEntity(Resume resume) {
        ResumesResponseDTO resumesDTO = new ResumesResponseDTO();
        resumesDTO.setTitle(resume.getTitle());
        resumesDTO.setResume(resume.getDescription());
        resumesDTO
                .setTagsResponse(resume.getResumeTags().stream().map(TagDTO::fromEntity).collect(Collectors.toList()));
        return resumesDTO;
    }
}
