package com.LearnTools.LearnToolsApi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LearnTools.LearnToolsApi.model.entidades.Prompt;

@Repository
public interface PromptRepository extends JpaRepository<Prompt, Integer> {
    public Prompt findByName(String name);
}
