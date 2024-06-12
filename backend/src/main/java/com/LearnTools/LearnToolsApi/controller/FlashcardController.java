package com.LearnTools.LearnToolsApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.FlashcardDTO;
import com.LearnTools.LearnToolsApi.handler.BussinessException;
import com.LearnTools.LearnToolsApi.model.entidades.FlashCardTag;
import com.LearnTools.LearnToolsApi.model.entidades.Flashcard;
import com.LearnTools.LearnToolsApi.model.entidades.Tag;
import com.LearnTools.LearnToolsApi.model.repository.FlashCardTagRepository;
import com.LearnTools.LearnToolsApi.model.repository.FlashcardRepository;
import com.LearnTools.LearnToolsApi.model.repository.TagRepository;

@RestController
public class FlashcardController {
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
            Tag tag = tagRepository.findById(tagID).orElseThrow(() -> new BussinessException("tag não encontrada"));
            FlashCardTag flashCardTag = new FlashCardTag();
            flashCardTag.setFlashcard(flashcard);
            flashCardTag.setTag(tag);
            flashCardTagRepository.save(flashCardTag);
        }
    }
}
