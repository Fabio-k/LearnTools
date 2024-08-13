package com.LearnTools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.LearnTools.LearnToolsApi.controller.dto.Response.AssistentResponse;
import com.LearnTools.LearnToolsApi.model.entidades.Assistent;
import com.LearnTools.LearnToolsApi.model.repository.AssistentRepository;
import com.LearnTools.LearnToolsApi.services.AssistentService;

public class AssistentServiceTest {

    @Mock
    private AssistentRepository assistentRepository;

    @InjectMocks
    private AssistentService assistentService;

    public AssistentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAssistents() {
        // Arrange
        List<Assistent> assistents = new ArrayList<>();
        assistents.add(new Assistent("John Doe"));
        assistents.add(new Assistent("Jane Smith"));

        when(assistentRepository.findAll()).thenReturn(assistents);

        // Act
        List<AssistentResponse> response = assistentService.getAllAssistents();

        // Assert
        assertEquals(2, response.size());
        assertEquals("John Doe", response.get(0).getName());
        assertEquals("Jane Smith", response.get(1).getName());
    }
}