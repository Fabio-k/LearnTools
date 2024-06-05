package com.LearnTools.LearnToolsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LearnTools.LearnToolsApi.model.Flashcard;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {

}
