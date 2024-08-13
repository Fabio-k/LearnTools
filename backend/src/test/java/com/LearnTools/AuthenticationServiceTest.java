package com.LearnTools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.LearnTools.LearnToolsApi.controller.dto.Request.SignInRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Request.SignUpRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.JwtAuthenticationResponse;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.model.entidades.Role;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;
import com.LearnTools.LearnToolsApi.services.AuthenticationService;
import com.LearnTools.LearnToolsApi.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthenticationServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);
        authenticationManager = mock(AuthenticationManager.class);
        authenticationService = new AuthenticationService(userRepository, passwordEncoder, jwtService,
                authenticationManager, null, null);
    }

    @Test
    void testSignup_shouldReturnJwtAuthenticationResponse() {
        // Arrange
        SignUpRequest request = new SignUpRequest("John Doe", "johndoe", "password");
        User user = new User("John Doe", "johndoe", "encodedPassword", Role.USER.name());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        // Act
        JwtAuthenticationResponse response = authenticationService.signup(request);

        // Assert
        assertEquals("jwtToken", response.getToken());
    }

    @Test
    void testSignup_withGithubAccountPrefix_shouldThrowBusinessException() {
        // Arrange
        SignUpRequest request = new SignUpRequest("John Doe", "github_johndoe", "password");

        // Act & Assert
        BusinessException exception = org.junit.jupiter.api.Assertions.assertThrows(BusinessException.class,
                () -> authenticationService.signup(request));
        assertEquals("github_ is a reserved prefix", exception.getMessage());
    }

    @Test
    void testSignup_usernameAlreadyExist_shouldThrowBusinessException() {
        SignUpRequest request = new SignUpRequest("John Doe", "github_johndoe", "password");
        User user = new User("John Doe", "johndoe", "encodedPassword", Role.USER.name());
        when(userRepository.findByUsername("John Doe")).thenReturn(Optional.of(user));

        assertThrows(BusinessException.class, () -> authenticationService.signup(request));
    }

    @Test
    void testSignIn_shouldReturnJwtAuthenticationResponse() {
        // Arrange
        SignInRequest request = new SignInRequest("johndoe", "password");
        User user = new User("John Doe", "johndoe", "encodedPassword", Role.USER.name());
        when(userRepository.findByUsername("johndoe")).thenReturn(java.util.Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        // Act
        JwtAuthenticationResponse response = authenticationService.signIn(request);

        // Assert
        assertEquals("jwtToken", response.getToken());
    }

    @Test
    void testSignIn_withInvalidUsername_shouldThrowBusinessException() {
        // Arrange
        SignInRequest request = new SignInRequest("johndoe", "password");
        when(userRepository.findByUsername("johndoe")).thenReturn(java.util.Optional.empty());

        // Act & Assert
        BusinessException exception = org.junit.jupiter.api.Assertions.assertThrows(BusinessException.class,
                () -> authenticationService.signIn(request));
        assertEquals("usuárioInválido", exception.getMessage());
    }
}