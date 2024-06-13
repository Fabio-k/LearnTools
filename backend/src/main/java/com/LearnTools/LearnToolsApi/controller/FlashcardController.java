package com.LearnTools.LearnToolsApi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.FlashcardDTO;
import com.LearnTools.LearnToolsApi.controller.dto.FlashcardResponseDTO;
import com.LearnTools.LearnToolsApi.handler.BussinessException;
import com.LearnTools.LearnToolsApi.model.entidades.FlashCardTag;
import com.LearnTools.LearnToolsApi.model.entidades.Flashcard;
import com.LearnTools.LearnToolsApi.model.entidades.Tag;
import com.LearnTools.LearnToolsApi.model.repository.FlashCardTagRepository;
import com.LearnTools.LearnToolsApi.model.repository.FlashcardRepository;
import com.LearnTools.LearnToolsApi.model.repository.TagRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class FlashcardController {
    @Autowired
    private FlashcardRepository repository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    FlashCardTagRepository flashCardTagRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/flashcards")
    public List<FlashcardResponseDTO> getFlashcards(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        if (username != null) {
            List<Flashcard> flashcards = repository.findAllByUserUsername(username);
            return flashcards.stream().map(FlashcardResponseDTO::fromEntity).collect(Collectors.toList());
        }
        throw new BussinessException("User is not authenticated");
    }

    @Transactional
    @PostMapping("/flashcards")
    public void postFlashcard(@RequestBody FlashcardDTO flashcardDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        Flashcard flashcard = new Flashcard(flashcardDTO.getQuestion(), flashcardDTO.getAnswer());
        flashcard.setUser(userRepository.findByUsername(userDetails.getUsername()));
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
