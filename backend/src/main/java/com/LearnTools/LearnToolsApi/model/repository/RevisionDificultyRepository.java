package com.LearnTools.LearnToolsApi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LearnTools.LearnToolsApi.model.entidades.RevisionDificulty;

@Repository
public interface RevisionDificultyRepository extends JpaRepository<RevisionDificulty, Integer> {

}
