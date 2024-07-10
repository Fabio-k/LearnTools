package com.LearnTools.LearnToolsApi.controller;

import java.util.List;

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

import com.LearnTools.LearnToolsApi.controller.dto.Request.FlashcardRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.FlashcardResponse;
import com.LearnTools.LearnToolsApi.services.FlashcardService;

@RestController
@RequestMapping("/flashcards")
public class FlashcardController {
    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @GetMapping()
    public List<FlashcardResponse> getFlashcards(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return flashcardService.getFlashcards(username);
    }

    @Transactional
    @PostMapping()
    public void postFlashcard(@RequestBody FlashcardRequest flashcardDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        flashcardService.postFlashcard(flashcardDTO, userDetails.getUsername());
    }

    @DeleteMapping("/{flashcardid}")
    public void deleteFlashcard(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String flashcardid) {
        flashcardService.deleteFlashcard(userDetails.getUsername(), Integer.parseInt(flashcardid));
        ;
    }
}
