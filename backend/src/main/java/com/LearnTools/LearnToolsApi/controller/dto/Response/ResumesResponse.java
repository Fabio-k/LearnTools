package com.LearnTools.LearnToolsApi.controller.dto.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.LearnTools.LearnToolsApi.controller.dto.TagReqRes;
import com.LearnTools.LearnToolsApi.model.entidades.Resume;

public class ResumesResponse {

    private String title;
    private String resume;
    private Integer id;
    private List<TagReqRes> tags = new ArrayList<>();

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

    public List<TagReqRes> getTags() {
        return tags;
    }

    public void setTagsResponse(List<TagReqRes> tags) {
        this.tags = tags;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static ResumesResponse fromEntity(Resume resume) {
        ResumesResponse resumesDTO = new ResumesResponse();
        resumesDTO.setTitle(resume.getTitle());
        resumesDTO.setResume(resume.getDescription());
        resumesDTO.setId(resume.getId());
        resumesDTO
                .setTagsResponse(
                        resume.getResumeTags().stream().map(TagReqRes::fromEntity).collect(Collectors.toList()));
        return resumesDTO;
    }

}