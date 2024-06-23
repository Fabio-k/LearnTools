package com.LearnTools.LearnToolsApi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.FlashcardDTO;
import com.LearnTools.LearnToolsApi.controller.dto.FlashcardResponseDTO;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.model.entidades.FlashCardTag;
import com.LearnTools.LearnToolsApi.model.entidades.Flashcard;
import com.LearnTools.LearnToolsApi.model.entidades.Tag;
import com.LearnTools.LearnToolsApi.model.repository.FlashCardTagRepository;
import com.LearnTools.LearnToolsApi.model.repository.FlashcardRepository;
import com.LearnTools.LearnToolsApi.model.repository.TagRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;

@RestController
@RequestMapping("/flashcards")
public class FlashcardController {
    private final FlashcardRepository repository;
    private final TagRepository tagRepository;
    private final FlashCardTagRepository flashCardTagRepository;
    private final UserRepository userRepository;

    public FlashcardController(FlashcardRepository repository, TagRepository tagRepository,
            FlashCardTagRepository flashCardTagRepository, UserRepository userRepository) {
        this.repository = repository;
        this.tagRepository = tagRepository;
        this.flashCardTagRepository = flashCardTagRepository;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public List<FlashcardResponseDTO> getFlashcards(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        List<Flashcard> flashcards = repository.findAllByUserUsername(username);
        return flashcards.stream().map(FlashcardResponseDTO::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping()
    public void postFlashcard(@RequestBody FlashcardDTO flashcardDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        Flashcard flashcard = new Flashcard(flashcardDTO.getQuestion(), flashcardDTO.getAnswer());

        flashcard.setUser(userRepository.findByUsername(userDetails.getUsername()));

        createFlashcardTag(flashcardDTO, userDetails, flashcard);
        repository.save(flashcard);
    }

    private void createFlashcardTag(FlashcardDTO flashcardDTO, UserDetails userDetails, Flashcard flashcard) {
        List<Tag> userTags = tagRepository.findAllByUserUsername(userDetails.getUsername());
        for (String tagName : flashcardDTO.getTagsName()) {
            Optional<Tag> tag = userTags.stream().filter(t -> t.getName().equals(tagName)).findFirst();
            if (tag.isEmpty())
                throw new BusinessException("tag não encontrada");

            FlashCardTag flashCardTag = new FlashCardTag();
            flashCardTag.setFlashcard(flashcard);
            flashCardTag.setTag(tag.get());
            flashCardTagRepository.save(flashCardTag);
        }
    }

    @Transactional
    @DeleteMapping("/{flashcardid}")
    public void deleteFlashcard(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String flashcardid) {
        String username = userDetails.getUsername();
        List<Flashcard> flashcards = repository.findAllByUserUsername(username);
        Optional<Flashcard> selectedFlashcard = flashcards.stream()
                .filter(t -> t.getId() == Integer.parseInt(flashcardid))
                .findFirst();
        if (selectedFlashcard.isEmpty())
            throw new BusinessException("Flashcard não encontrado");
        repository.deleteById(Integer.parseInt(flashcardid));
    }
}
