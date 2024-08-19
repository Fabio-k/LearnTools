package com.LearnTools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.LearnTools.LearnToolsApi.controller.dto.AssistentDto;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.handler.CampoObrigatorioException;
import com.LearnTools.LearnToolsApi.model.entidades.Assistent;
import com.LearnTools.LearnToolsApi.model.entidades.Prompt;
import com.LearnTools.LearnToolsApi.model.repository.AssistentRepository;
import com.LearnTools.LearnToolsApi.model.repository.PromptRepository;
import com.LearnTools.LearnToolsApi.services.PromptService;

public class PromptServiceTest {

    @Mock
    private AssistentRepository assistentRepository;

    @InjectMocks
    private PromptService promptService;

    @Mock
    private PromptRepository promptRepository;

    public PromptServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAssistents_success() {
        // Arrange
        Prompt prompt1 = new Prompt();
        Prompt prompt2 = new Prompt();
        Assistent assistent1 = new Assistent();
        Assistent assistent2 = new Assistent();
        prompt1.setName("John Doe");
        prompt2.setName("Jane Smith");
        assistent1.setPrompt(prompt1);
        assistent2.setPrompt(prompt2);
        List<Assistent> assistents = new ArrayList<>();
        assistents.add(assistent1);
        assistents.add(assistent2);

        when(assistentRepository.findAll()).thenReturn(assistents);

        // Act
        List<AssistentDto> response = promptService.getAllPrompts();

        // Assert
        assertEquals(2, response.size());
        assertEquals("John Doe", response.get(0).getName());
        assertEquals("Jane Smith", response.get(1).getName());
    }

    @Test
    public void testPatchAssistent_success() {
        String prompt = "it functions!";
        String name = "assistent";

        Prompt prompt1 = new Prompt();
        prompt1.setName(name);
        prompt1.setPrompt("your role is to be a test to check if I can modify you");
        Optional<Prompt> optionalPrompt = Optional.of(prompt1);

        AssistentDto request = new AssistentDto();
        request.setPrompt(prompt);
        request.setId(1);

        when(promptRepository.findById(1)).thenReturn(optionalPrompt);

        AssistentDto response = promptService.patchPrompt(request);

        assertEquals(prompt, response.getPrompt());
        assertEquals(name, response.getName());
    }

    @Test
    public void testPatchAssistent_ThrowCampoObrigatorioException() {
        AssistentDto request = new AssistentDto();
        request.setPrompt("hello");
        request.setName("a name");
        assertThrows(CampoObrigatorioException.class, () -> promptService.patchPrompt(request));
    }

    @Test
    public void testPatchAssistent_ThrowBussinessException() {
        AssistentDto request = new AssistentDto();
        request.setPrompt("hello");
        request.setName("a name");
        request.setDescription("a description");
        request.setId(1);

        Prompt prompt = new Prompt();
        prompt.setId(1);
        prompt.setName("a name");
        prompt.setPrompt("a prompt");

        when(promptRepository.findById(1)).thenReturn(Optional.of(prompt));

        assertThrows(BusinessException.class, () -> promptService.patchPrompt(request));
    }
}