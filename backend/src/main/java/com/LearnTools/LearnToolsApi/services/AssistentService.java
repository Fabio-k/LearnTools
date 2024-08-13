package com.LearnTools.LearnToolsApi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.LearnTools.LearnToolsApi.controller.dto.Response.AssistentResponse;
import com.LearnTools.LearnToolsApi.model.entidades.Assistent;
import com.LearnTools.LearnToolsApi.model.repository.AssistentRepository;

@Service
public class AssistentService {
    private final AssistentRepository assistentRepository;

    public AssistentService(AssistentRepository assistentRepository) {
        this.assistentRepository = assistentRepository;
    }

    public List<AssistentResponse> getAllAssistents() {
        List<Assistent> assistent = assistentRepository.findAll();
        List<AssistentResponse> response = assistent.stream().map(AssistentResponse::fromEntity)
                .collect(Collectors.toList());
        return response;
    }
}
