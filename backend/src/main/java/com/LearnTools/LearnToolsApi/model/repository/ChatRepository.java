package com.LearnTools.LearnToolsApi.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LearnTools.LearnToolsApi.model.entidades.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    List<Chat> findAllChatByUserUsername(String username);
}