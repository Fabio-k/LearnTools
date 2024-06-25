package com.LearnTools.LearnToolsApi.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.entidades.UserRoles;

public interface UserRolesRepository extends JpaRepository<UserRoles, Integer> {
    List<UserRoles> findAllByUser(User user);
}
