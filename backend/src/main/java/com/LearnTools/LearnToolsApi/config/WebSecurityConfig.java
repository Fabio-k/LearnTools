package com.LearnTools.LearnToolsApi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    public static final String[] ENDPOINTS_WHITELIST = {
            "/assistents/**",
            "/login/**",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Autowired
    private SecurityDatabaseService securityService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().hasRole("USER")).httpBasic(withDefaults());
        return http.build();
    }

}