package com.LearnTools.LearnToolsApi.controller.dto.Response;

import java.util.List;
import java.util.Map;

import com.LearnTools.LearnToolsApi.controller.dto.TagReqRes;

public class AnaliticsResponse {
    private Integer length;
    private Map<String, Integer> tagByFlashcard;
    private List<TagReqRes> tags;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Map<String, Integer> getTagByFlashcard() {
        return tagByFlashcard;
    }

    public void setTagByFlashcard(Map<String, Integer> tagByFlashcard) {
        this.tagByFlashcard = tagByFlashcard;
    }

    public List<TagReqRes> getTags() {
        return tags;
    }

    public void setTags(List<TagReqRes> tags) {
        this.tags = tags;
    }

}
