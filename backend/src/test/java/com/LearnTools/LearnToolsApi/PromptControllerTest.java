package com.LearnTools.LearnToolsApi;

import com.LearnTools.LearnToolsApi.controller.AssistentController;
import com.LearnTools.LearnToolsApi.handler.GlobalExecptionHandler;
import com.LearnTools.LearnToolsApi.model.entidades.Prompt;
import com.LearnTools.LearnToolsApi.model.repository.PromptRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(AssistentController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PromptControllerTest {

    @MockBean
    private PromptRepository repository;

    @InjectMocks
    private AssistentController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GlobalExecptionHandler globalExceptionHandler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(globalExceptionHandler).build();
    }

    @Test
    public void testListAssistents() throws Exception {
        Prompt prompt1 = new Prompt("prompt 1", "teste");
        Prompt prompt2 = new Prompt("prompt 2", "teste");
        List<Prompt> prompts = Arrays.asList(prompt1, prompt2);

        when(repository.findAll()).thenReturn(prompts);

        mockMvc.perform(MockMvcRequestBuilders.get("/assistents")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("prompt 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("prompt 2"));
    }

    @Test
    public void testGetAssistentByName() throws Exception {
        Prompt assistent = new Prompt("Assistent 1", "teste");

        when(repository.findByName("Assistent 1")).thenReturn(assistent);

        mockMvc.perform(MockMvcRequestBuilders.get("/assistents/{name}", "Assistent 1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Assistent 1"));
    }

    @Test
    public void testGetAssistentByName_NotFound() throws Exception {
        when(repository.findByName("Nonexistent Assistent")).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/assistents/{name}", "Nonexistent Assistent")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }
}