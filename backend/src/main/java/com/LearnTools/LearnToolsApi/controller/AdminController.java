package com.LearnTools.LearnToolsApi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.UserDTO;
import com.LearnTools.LearnToolsApi.model.entidades.Flashcard;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.repository.FlashcardRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;
    private final FlashcardRepository flashcardRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository, FlashcardRepository flashcardRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.flashcardRepository = flashcardRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @DeleteMapping("/users/{username}")
    public void deleteUser(@PathVariable String username) {
        userRepository.deleteByUsername(username);
    }

    @PostMapping("/signup")
    public void postUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO.getName(), userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(new ArrayList<>());
        user.getRoles().add("ADMIN");
        userRepository.save(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/flashcards")
    public List<Flashcard> getAllFlashcards() {
        return flashcardRepository.findAll();
    }

}
