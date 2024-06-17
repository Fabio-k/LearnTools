package com.LearnTools.LearnToolsApi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LearnTools.LearnToolsApi.model.entidades.ResumeTag;

@Repository
public interface ResumeTagRepository extends JpaRepository<ResumeTag, Integer> {

}
