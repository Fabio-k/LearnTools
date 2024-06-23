package com.LearnTools.LearnToolsApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.model.entidades.Prompt;
import com.LearnTools.LearnToolsApi.model.repository.PromptRepository;

import java.util.List;

@RestController
@RequestMapping("/assistents")
public class AssistentController {
    @Autowired
    PromptRepository repository;

    @GetMapping()
    public List<Prompt> listAssistents() {
        return repository.findAll();
    }

    @GetMapping("/{name}")
    public Prompt getAssistentByName(@PathVariable String name) {
        Prompt prompts = repository.findByName(name);
        if (prompts == null) {
            throw new BusinessException("Assistente não encontrado");
        }
        return prompts;
    }
}
