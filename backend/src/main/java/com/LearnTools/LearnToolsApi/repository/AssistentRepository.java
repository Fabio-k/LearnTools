package com.LearnTools.LearnToolsApi.repository;

import com.LearnTools.LearnToolsApi.model.Assistent; // Import the Assistent class

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistentRepository extends JpaRepository<Assistent, Integer> {
    public Assistent findByName(String name);
}
