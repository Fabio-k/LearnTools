package com.LearnTools.LearnToolsApi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.LearnTools.LearnToolsApi.controller.dto.AssistentDto;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.handler.CampoObrigatorioException;
import com.LearnTools.LearnToolsApi.model.entidades.Assistent;
import com.LearnTools.LearnToolsApi.model.entidades.Prompt;
import com.LearnTools.LearnToolsApi.model.repository.AssistentRepository;
import com.LearnTools.LearnToolsApi.model.repository.PromptRepository;

@Service
public class PromptService {
    private final AssistentRepository assistentRepository;
    private final PromptRepository promptRepository;

    public PromptService(AssistentRepository assistentRepository, PromptRepository promptRepository) {
        this.assistentRepository = assistentRepository;
        this.promptRepository = promptRepository;
    }

    public List<AssistentDto> getAllPrompts() {
        List<Assistent> assistent = assistentRepository.findAll();
        List<AssistentDto> response = assistent.stream().map(AssistentDto::fromEntity)
                .collect(Collectors.toList());
        return response;
    }

    public AssistentDto patchPrompt(AssistentDto req) {
        if (req.getId() == null)
            throw new CampoObrigatorioException("id");
        Optional<Prompt> optionalAssistent = promptRepository.findById(req.getId());
        if (optionalAssistent.isEmpty())
            throw new BusinessException("Assistent not found");

        AssistentDto response = new AssistentDto();
        Prompt prompt = optionalAssistent.get();
        if (req.getName() != null)
            prompt.setName(req.getName());
        if (req.getPrompt() != null)
            prompt.setPrompt(req.getPrompt());
        if (req.getDescription() != null) {
            if (prompt.getAssistent() == null)
                throw new BusinessException("Este prompt não é um assistente");
            prompt.getAssistent().setDescription(req.getDescription());
            response.setDescription(prompt.getAssistent().getDescription());
        }
        promptRepository.save(prompt);

        response.setId(prompt.getId());
        response.setName(prompt.getName());
        response.setPrompt(prompt.getPrompt());
        return response;
    }
}
