package com.LearnTools.LearnToolsApi.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.LearnTools.LearnToolsApi.model.entidades.Role;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.entidades.UserRoles;
import com.LearnTools.LearnToolsApi.model.repository.RolesRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRolesRepository;

@Service
public class SecurityDatabaseService implements UserDetailsService {
    private final UserRepository repository;
    private final UserRolesRepository userRolesRepository;

    public SecurityDatabaseService(UserRepository repository, UserRolesRepository userRolesRepository,
            RolesRepository rolesRepository) {
        this.repository = repository;
        this.userRolesRepository = userRolesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User userEntity = repository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        List<UserRoles> userRoles = userRolesRepository.findAllByUser(userEntity);
        userRoles.forEach(u -> {
            Role role = u.getRole();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });

        UserDetails user = new org.springframework.security.core.userdetails.User(userEntity.getUsername(),
                userEntity.getPassword(), authorities);

        return user;
    }
}
