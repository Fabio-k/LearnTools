package com.LearnTools.LearnToolsApi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.LearnTools.LearnToolsApi.model.entidades.Role;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;
import com.LearnTools.LearnToolsApi.client.GithubApiClient;
import com.LearnTools.LearnToolsApi.client.GithubWebClient;
import com.LearnTools.LearnToolsApi.controller.dto.Client.GithubAcessTokenRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Client.GithubSignInRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Client.GithubTokenResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Client.GithubUserResponse;
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
        private final GithubWebClient githubWebClient;
        private final GithubApiClient githubApiClient;

        @Value("${github.private.key}")
        private String client_secret;

        private String github_account_prefix = "github_";

        public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                        JwtService jwtService,
                        AuthenticationManager authenticationManager, GithubWebClient githubWebClient,
                        GithubApiClient githubApiClient) {
                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
                this.jwtService = jwtService;
                this.authenticationManager = authenticationManager;
                this.githubWebClient = githubWebClient;
                this.githubApiClient = githubApiClient;
        }

        public JwtAuthenticationResponse signup(SignUpRequest request) {
                if (request.getUsername().startsWith(github_account_prefix)) {
                        throw new BusinessException("github_ is a reserved prefix");
                }
                User user = new User(request.getName(), request.getUsername(),
                                passwordEncoder.encode(request.getPassword()),
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

        public JwtAuthenticationResponse loginGithub(GithubSignInRequest request) {

                GithubAcessTokenRequest githubAcessTokenRequest = new GithubAcessTokenRequest(request.getCode(),
                                client_secret,
                                request.getClientId());
                GithubTokenResponse githubTokenResponse = githubWebClient.getToken("application/json",
                                githubAcessTokenRequest);

                GithubUserResponse githubUserResponse = githubApiClient
                                .getUserInformation("Bearer " + githubTokenResponse.getAccess_token());

                User user = getGithubUser(githubUserResponse);

                String jwt = jwtService.generateToken(user);
                JwtAuthenticationResponse response = new JwtAuthenticationResponse(jwt);

                return response;

        }

        private User getGithubUser(GithubUserResponse response) {
                Optional<User> githubUser = userRepository.findByUsername(github_account_prefix + response.getId());
                if (githubUser.isPresent()) {
                        return githubUser.get();
                }

                User user = new User();
                user.setUsername(github_account_prefix + response.getId());
                user.setGithubUsername(response.getLogin());
                user.setName(response.getName());
                user.setImage(response.getAvatar_url());
                user.setRole(Role.USER.name());
                return userRepository.save(user);

        }
}
