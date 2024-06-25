package com.LearnTools.LearnToolsApi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.LearnTools.LearnToolsApi.model.entidades.Role;

public interface RolesRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
