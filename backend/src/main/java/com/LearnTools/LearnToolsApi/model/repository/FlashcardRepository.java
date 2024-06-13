package com.LearnTools.LearnToolsApi.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LearnTools.LearnToolsApi.model.entidades.Flashcard;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {

    List<Flashcard> findAllByUserUsername(String username);

}
