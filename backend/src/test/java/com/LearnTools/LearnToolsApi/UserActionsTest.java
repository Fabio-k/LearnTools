package com.LearnTools.LearnToolsApi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.LearnTools.LearnToolsApi.client.AiClient;
import com.LearnTools.LearnToolsApi.controller.dto.AiResumeResponse;
import com.LearnTools.LearnToolsApi.controller.dto.MessageDTO;
import com.LearnTools.LearnToolsApi.controller.dto.ResumesDTO;
import com.LearnTools.LearnToolsApi.controller.dto.TagDTO;
import com.LearnTools.LearnToolsApi.controller.dto.UserDTO;
import com.LearnTools.LearnToolsApi.controller.dto.AiResumeResponse.Message;
import com.LearnTools.LearnToolsApi.model.entidades.Prompt;
import com.LearnTools.LearnToolsApi.model.entidades.Role;
import com.LearnTools.LearnToolsApi.model.repository.PromptRepository;
import com.LearnTools.LearnToolsApi.model.repository.RolesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
public class UserActionsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PromptRepository promptRepository;

    @MockBean
    private AiClient aiClient;

    private final String USERNAME = "teste";
    private final String PASSWORD = "teste";
    private final String TAGNAME = "teste";

    private Integer resumeID;

    @Test
    @Order(1)
    public void testSignUpUser() throws Exception {
        Role role = new Role();
        role.setName("USER");
        rolesRepository.save(role);
        UserDTO userDTO = new UserDTO("teste user", USERNAME, PASSWORD);
        String userJson = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/signup").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void testTagCreation() throws Exception {
        TagDTO tagDTO = new TagDTO(TAGNAME, "color");
        String tagJson = objectMapper.writeValueAsString(tagDTO);
        mockMvc.perform(post("/tags").contentType(MediaType.APPLICATION_JSON).content(tagJson)
                .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void testResumeCreation() throws Exception {
        List<String> tagNames = new ArrayList<>();
        tagNames.add(TAGNAME);
        ResumesDTO resumesDTO = new ResumesDTO("resume", "resume description", tagNames);
        String resumeJson = objectMapper.writeValueAsString(resumesDTO);

        MvcResult result = mockMvc.perform(post("/resumes").contentType(MediaType.APPLICATION_JSON).content(resumeJson)
                .with(httpBasic(USERNAME, PASSWORD))).andExpect(status().isOk()).andReturn();

        resumeID = Integer.parseInt(result.getResponse().getContentAsString());
    }

    @Test
    @Order(4)
    public void testChatCreation() throws Exception {
        AiResumeResponse aiResumeResponse = new AiResumeResponse();
        Message message = new Message();
        message.setRole("assistent");
        message.setContent("response test");
        aiResumeResponse.setMessage(message);

        Prompt prompt = new Prompt("asistent test", "teste");
        Prompt savedPrompt = promptRepository.save(prompt);

        Prompt resumePrompt = new Prompt("prompt resume base", "teste");
        promptRepository.save(resumePrompt);

        when(aiClient.getResumeResponse(any())).thenReturn(aiResumeResponse);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setAssistentID(savedPrompt.getId());
        messageDTO.setMessage("test message");
        messageDTO.setResumeID(1);
        messageDTO.setModel("model test");

        String messageJson = objectMapper.writeValueAsString(messageDTO);

        mockMvc.perform(post("/chat").contentType(MediaType.APPLICATION_JSON).content(messageJson)
                .with(httpBasic(USERNAME, PASSWORD))).andExpect(status().isOk());
    }
}
