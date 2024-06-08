package com.LearnTools.LearnToolsApi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LearnTools.LearnToolsApi.model.entidades.Assistent;

@Repository
public interface AssistentRepository extends JpaRepository<Assistent, Integer> {
    public Assistent findByName(String name);
}
