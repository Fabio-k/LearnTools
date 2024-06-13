package com.LearnTools.LearnToolsApi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.UserDTO;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class UserController {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public List<User> getMethodName() {
        return repository.findAll();
    }

    @PostMapping("/login")
    public void postUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO.getName(), userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(new ArrayList<>());
        user.getRoles().add("USER");
        repository.save(user);
    }

    @Transactional
    @DeleteMapping("/login/{username}")
    public void deleteUser(@PathVariable String username) {
        repository.deleteByUsername(username);
    }

}
