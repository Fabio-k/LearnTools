package com.LearnTools.LearnToolsApi.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LearnTools.LearnToolsApi.model.entidades.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findAllByUserUsername(String username);
}
