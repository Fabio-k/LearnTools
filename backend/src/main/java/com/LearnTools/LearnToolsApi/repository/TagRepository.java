package com.LearnTools.LearnToolsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LearnTools.LearnToolsApi.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

}
