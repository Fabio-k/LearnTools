package com.LearnTools.LearnToolsApi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.client.AiClient;
import com.LearnTools.LearnToolsApi.controller.dto.AiTagResponse;
import com.LearnTools.LearnToolsApi.controller.dto.UserDTO;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.model.entidades.Flashcard;
import com.LearnTools.LearnToolsApi.model.entidades.Role;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.entidades.UserRoles;
import com.LearnTools.LearnToolsApi.model.repository.FlashcardRepository;
import com.LearnTools.LearnToolsApi.model.repository.RolesRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRolesRepository;

import java.util.List;
import java.util.Optional;

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
    private final AiClient aiClient;
    private final UserRolesRepository userRolesRepository;
    private final RolesRepository rolesRepository;

    public AdminController(UserRepository userRepository, FlashcardRepository flashcardRepository,
            PasswordEncoder passwordEncoder, AiClient aiClient, RolesRepository rolesRepository,
            UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.flashcardRepository = flashcardRepository;
        this.passwordEncoder = passwordEncoder;
        this.aiClient = aiClient;
        this.rolesRepository = rolesRepository;
        this.userRolesRepository = userRolesRepository;
    }

    @Transactional
    @DeleteMapping("/users/{username}")
    public void deleteUser(@PathVariable String username) {
        userRepository.deleteByUsername(username);
    }

    @PostMapping("/signup")
    public void postUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO.getName(), userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        Optional<Role> role = rolesRepository.findById(userDTO.getRoleId());
        if (role.isEmpty())
            throw new BusinessException("role not found");
        UserRoles userRoles = new UserRoles();
        userRoles.setRole(role.get());
        userRoles.setUser(user);
        userRolesRepository.save(userRoles);

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
