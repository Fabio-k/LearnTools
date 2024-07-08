package com.LearnTools.LearnToolsApi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
public class FlashcardService {
    private final FlashcardRepository flashcardRepository;
    private final TagRepository tagRepository;
    private final FlashCardTagRepository flashCardTagRepository;
    private final UserRepository userRepository;

    public FlashcardService(FlashcardRepository flashcardRepository, TagRepository tagRepository,
            FlashCardTagRepository flashCardTagRepository, UserRepository userRepository) {
        this.flashcardRepository = flashcardRepository;
        this.tagRepository = tagRepository;
        this.flashCardTagRepository = flashCardTagRepository;
        this.userRepository = userRepository;
    }

    public List<FlashcardResponseDTO> getFlashcards(String username) {
        List<Flashcard> flashcards = flashcardRepository.findAllByUserUsername(username);
        return flashcards.stream().map(FlashcardResponseDTO::fromEntity).collect(Collectors.toList());
    }

    public void postFlashcard(FlashcardDTO flashcardDTO, String username) {
        Flashcard flashcard = new Flashcard(flashcardDTO.getQuestion(), flashcardDTO.getAnswer());
        flashcard.setUser(userRepository.findByUsername(username));
        createFlashcardTag(flashcardDTO, username, flashcard);
        flashcardRepository.save(flashcard);
    }

    @Transactional
    public void deleteFlashcard(String username, Integer flashcardid) {
        List<Flashcard> flashcards = flashcardRepository.findAllByUserUsername(username);
        Optional<Flashcard> selectedFlashcard = flashcards.stream()
                .filter(t -> t.getId() == flashcardid)
                .findFirst();
        if (selectedFlashcard.isEmpty())
            throw new BusinessException("Flashcard não encontrado");
        flashcardRepository.deleteById(flashcardid);
    }

    private void createFlashcardTag(FlashcardDTO flashcardDTO, String username, Flashcard flashcard) {
        List<Tag> userTags = tagRepository.findAllByUserUsername(username);
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
}
