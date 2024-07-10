package com.LearnTools.LearnToolsApi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.client.AiClient;
import com.LearnTools.LearnToolsApi.controller.dto.Response.AiTagResponse;
import com.LearnTools.LearnToolsApi.model.entidades.Flashcard;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.repository.FlashcardRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;
    private final FlashcardRepository flashcardRepository;
    private final AiClient aiClient;

    public AdminController(UserRepository userRepository, FlashcardRepository flashcardRepository,
            AiClient aiClient) {
        this.userRepository = userRepository;
        this.flashcardRepository = flashcardRepository;
        this.aiClient = aiClient;
    }

    @Transactional
    @DeleteMapping("/users/{username}")
    public void deleteUser(@PathVariable String username) {
        userRepository.deleteByUsername(username);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/flashcards")
    public List<Flashcard> getAllFlashcards() {
        return flashcardRepository.findAll();
    }

    @GetMapping("/ai")
    public AiTagResponse getAllAiTags() {
        return aiClient.getTags();
    }

}
