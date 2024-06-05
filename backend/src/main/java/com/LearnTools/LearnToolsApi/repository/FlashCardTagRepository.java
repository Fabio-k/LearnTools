package com.LearnTools.LearnToolsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LearnTools.LearnToolsApi.model.FlashCardTag;

@Repository
public interface FlashCardTagRepository extends JpaRepository<FlashCardTag, Integer> {

}