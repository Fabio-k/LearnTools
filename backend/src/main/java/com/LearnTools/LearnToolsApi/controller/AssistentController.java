package com.LearnTools.LearnToolsApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.handler.BussinessException;
import com.LearnTools.LearnToolsApi.model.entidades.Assistent;
import com.LearnTools.LearnToolsApi.model.repository.AssistentRepository;

import java.util.List;

@RestController
public class AssistentController {
    @Autowired
    AssistentRepository repository;

    @GetMapping("/assistents")
    public List<Assistent> listAssistents() {
        return repository.findAll();
    }

    @GetMapping("/assistents/{name}")
    public Assistent getAssistentByName(@PathVariable String name) {
        Assistent assistent = repository.findByName(name);
        if (assistent == null) {
            throw new BussinessException("Assistente não encontrado");
        }
        return assistent;
    }
}
