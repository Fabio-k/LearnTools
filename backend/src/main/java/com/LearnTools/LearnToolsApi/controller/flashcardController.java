package com.LearnTools.LearnToolsApi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.model.FlashCardTag;
import com.LearnTools.LearnToolsApi.model.Flashcard;
import com.LearnTools.LearnToolsApi.model.Tag;
import com.LearnTools.LearnToolsApi.repository.FlashCardTagRepository;
import com.LearnTools.LearnToolsApi.repository.FlashcardRepository;
import com.LearnTools.LearnToolsApi.repository.TagRepository;

@RestController
public class flashcardController {
    @Autowired
    private FlashcardRepository repository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    FlashCardTagRepository flashCardTagRepository;

    @PostMapping("/flashcards")
    public void postFlashcard(@RequestBody FlashcardDTO flashcardDTO) {
        Flashcard flashcard = new Flashcard(flashcardDTO.getQuestion(), flashcardDTO.getAnswer());
        repository.save(flashcard);
        for (Integer tagID : flashcardDTO.getTagsId()) {
            Tag tag = tagRepository.findById(tagID).orElseThrow(() -> new RuntimeException("tag not found"));
            FlashCardTag flashCardTag = new FlashCardTag();
            flashCardTag.setFlashcard(flashcard);
            flashCardTag.setTag(tag);
            flashCardTagRepository.save(flashCardTag);
        }
    }
}

class FlashcardDTO {
    private String question;
    private String answer;
    private List<Integer> tagsId = new ArrayList<>();

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public List<Integer> getTagsId() {
        return tagsId;
    }

}