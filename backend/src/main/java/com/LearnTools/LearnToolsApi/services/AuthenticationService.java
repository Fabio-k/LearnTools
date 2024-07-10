package com.LearnTools.LearnToolsApi.services;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.LearnTools.LearnToolsApi.model.entidades.Role;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;
import com.LearnTools.LearnToolsApi.controller.dto.Request.SignInRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Request.SignUpRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.JwtAuthenticationResponse;
import com.LearnTools.LearnToolsApi.handler.BusinessException;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public JwtAuthenticationResponse signup(SignUpRequest request) {
        User user = new User(request.getName(), request.getUsername(), passwordEncoder.encode(request.getPassword()),
                Role.USER.name());
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse response = new JwtAuthenticationResponse(jwt);
        return response;
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException("usuárioInválido"));

        String jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse response = new JwtAuthenticationResponse(jwt);
        return response;
    }
}
