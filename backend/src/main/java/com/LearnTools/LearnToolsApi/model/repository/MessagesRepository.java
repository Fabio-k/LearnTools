package com.LearnTools.LearnToolsApi.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LearnTools.LearnToolsApi.model.entidades.MessagesEntity;

@Repository
public interface MessagesRepository extends JpaRepository<MessagesEntity, Integer> {
    List<MessagesEntity> findAllByChatId(Integer chatId);
}
