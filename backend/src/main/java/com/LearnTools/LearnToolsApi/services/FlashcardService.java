package com.LearnTools.LearnToolsApi.services;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.LearnTools.LearnToolsApi.controller.dto.Request.FlashcardRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.AnaliticsResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Response.FlashcardResponse;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.model.entidades.FlashCardTag;
import com.LearnTools.LearnToolsApi.model.entidades.Flashcard;
import com.LearnTools.LearnToolsApi.model.entidades.Tag;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.repository.FlashCardTagRepository;
import com.LearnTools.LearnToolsApi.model.repository.FlashcardRepository;

@Service
public class FlashcardService {
    private final FlashcardRepository flashcardRepository;
    private final TagService tagService;
    private final FlashCardTagRepository flashCardTagRepository;

    private final UserService userService;

    public FlashcardService(FlashcardRepository flashcardRepository, TagService tagService,
            FlashCardTagRepository flashCardTagRepository, UserService userService) {
        this.flashcardRepository = flashcardRepository;
        this.tagService = tagService;
        this.flashCardTagRepository = flashCardTagRepository;
        this.userService = userService;
    }

    public List<FlashcardResponse> getFlashcards(String username) {
        List<Flashcard> flashcards = flashcardRepository.findAllByUserUsername(username);

        return flashcards.stream().map(FlashcardResponse::fromEntity).collect(Collectors.toList());
    }

    public AnaliticsResponse getAnalitics(String username) {
        AnaliticsResponse response = new AnaliticsResponse();
        List<Flashcard> flashcards = flashcardRepository.findAllByUserUsername(username);

        response.setTagByFlashcard(getTagByFlashcard(flashcards));
        response.setLength(flashcards.size());

        return response;
    }

    public void postFlashcard(FlashcardRequest flashcardDTO, String username) {
        Flashcard flashcard = new Flashcard(flashcardDTO.getQuestion(), flashcardDTO.getAnswer());

        User user = userService.getUser(username);
        Date now = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = calendar.getTime();

        flashcard.setUser(user);
        flashcard.setCreatedAt(now);
        flashcard.setRevision_date(tomorrow);

        flashcardRepository.save(flashcard);
        createFlashcardTag(flashcardDTO, username, flashcard);

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

    private void createFlashcardTag(FlashcardRequest flashcardDTO, String username, Flashcard flashcard) {
        List<Tag> userTags = tagService.getUserTags(username);
        List<String> requestTags = flashcardDTO.getTagsName();
        for (String tagName : requestTags) {
            Optional<Tag> tag = userTags.stream().filter(t -> t.getName().equalsIgnoreCase(tagName)).findFirst();
            if (tag.isEmpty())
                throw new BusinessException("tag não encontrada");

            FlashCardTag flashCardTag = new FlashCardTag();
            flashCardTag.setFlashcard(flashcard);
            flashCardTag.setTag(tag.get());
            flashCardTagRepository.save(flashCardTag);
        }
    }

    private Map<String, Integer> getTagByFlashcard(List<Flashcard> flashcards) {
        Map<String, Integer> tagByFlashcard = new HashMap<>();
        for (Flashcard flashcard : flashcards) {
            if (flashcard.getFlashCardTags().isEmpty()) {
                Integer noTagValue = tagByFlashcard.get("noTag");
                if (noTagValue == null) {
                    tagByFlashcard.put("noTag", 1);
                    continue;
                }
                tagByFlashcard.put("noTag", ++noTagValue);
            }

            for (FlashCardTag flashCardTag : flashcard.getFlashCardTags()) {
                String tag = flashCardTag.getTag().getName();
                if (tagByFlashcard.containsKey(tag)) {
                    Integer tagValue = tagByFlashcard.get(tag);
                    tagByFlashcard.put(tag, ++tagValue);
                    continue;
                }
                tagByFlashcard.put(tag, 1);
            }
        }
        return tagByFlashcard;
    }
}
