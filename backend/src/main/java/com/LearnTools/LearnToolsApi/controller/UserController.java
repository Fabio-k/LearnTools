package com.LearnTools.LearnToolsApi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.UserDTO;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class UserController {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    @Transactional
    public void postUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO.getName(), userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(new ArrayList<>());
        user.getRoles().add("USER");
        repository.save(user);
    }

    @DeleteMapping("/user")
    @Transactional
    public void deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        repository.deleteByUsername(userDetails.getUsername());
    }
}
