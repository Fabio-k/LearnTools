package com.LearnTools.LearnToolsApi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.LearnTools.LearnToolsApi.config.SecurityDatabaseService;
import com.LearnTools.LearnToolsApi.config.WebSecurityConfig;
import com.LearnTools.LearnToolsApi.controller.UserController;
import com.LearnTools.LearnToolsApi.controller.dto.UserDTO;
import com.LearnTools.LearnToolsApi.model.entidades.Role;
import com.LearnTools.LearnToolsApi.model.repository.RolesRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRolesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@WebMvcTest(controllers = UserController.class)
@Import(WebSecurityConfig.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserRolesRepository userRolesRepository;

    @MockBean
    private RolesRepository rolesRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private SecurityDatabaseService securityDatabaseService;

    @Test
    public void userController_postUser_success() throws Exception {
        Role role = new Role();
        role.setName("USER");
        when(rolesRepository.findByName("USER")).thenReturn(role);

        UserDTO userDTO = new UserDTO("Test Name", "TestUsername", "TestPassword");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(userDTO);
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    public void userController_postUser_conflict_noRole() throws Exception {
        when(rolesRepository.findByName("USER")).thenReturn(null);

        UserDTO userDTO = new UserDTO("Test Name", "TestUsername", "TestPassword");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(userDTO);
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isConflict());
    }

    @Test
    public void userController_postUser_conflict_noPassword() throws Exception {
        Role role = new Role();
        role.setName("USER");
        when(rolesRepository.findByName("USER")).thenReturn(role);

        UserDTO userDTO = new UserDTO("Test Name", "TestUsername", null);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(userDTO);
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isConflict());
    }
}
