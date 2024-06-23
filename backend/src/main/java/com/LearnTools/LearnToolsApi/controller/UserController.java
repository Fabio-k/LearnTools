package com.LearnTools.LearnToolsApi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.UserDTO;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.model.entidades.Role;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.entidades.UserRoles;
import com.LearnTools.LearnToolsApi.model.repository.RolesRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRolesRepository;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class UserController {
    private UserRepository repository;
    private UserRolesRepository userRolesRepository;
    private RolesRepository rolesRepository;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository repository, UserRolesRepository userRolesRepository,
            RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.userRolesRepository = userRolesRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    @Transactional
    public void postUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO.getName(), userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()));
        repository.save(user);
        Role role = rolesRepository.findByName("USER");
        if (role == null)
            throw new BusinessException("role not found");
        UserRoles userRoles = new UserRoles();
        userRoles.setRole(role);
        userRoles.setUser(user);
        userRolesRepository.save(userRoles);
    }

    @DeleteMapping("/user")
    @Transactional
    public void deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        repository.deleteByUsername(userDetails.getUsername());
    }
}
