package com.LearnTools.LearnToolsApi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.Request.GithubSignInRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Request.SignInRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Request.SignUpRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.JwtAuthenticationResponse;
import com.LearnTools.LearnToolsApi.handler.CampoObrigatorioException;
import com.LearnTools.LearnToolsApi.services.AuthenticationService;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class UserController {
    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @CrossOrigin
    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<JwtAuthenticationResponse> postUser(@RequestBody SignUpRequest request) {
        if (request.getUsername() == null)
            throw new CampoObrigatorioException("username");
        if (request.getPassword() == null)
            throw new CampoObrigatorioException("password");
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @CrossOrigin
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> postMethodName(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @CrossOrigin
    @PostMapping("/github")
    public ResponseEntity<JwtAuthenticationResponse> postMethodName(@RequestBody GithubSignInRequest request) {
        return ResponseEntity.ok(authenticationService.loginGithub(request));
    }

}
