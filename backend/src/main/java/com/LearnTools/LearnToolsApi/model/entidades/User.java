package com.LearnTools.LearnToolsApi.model.entidades;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tab_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Integer id;

    @Column(length = 50, name = "usr_name")
    private String name;

    @Column(length = 50, name = "usr_username")
    private String username;

    @Column(length = 255, name = "usr_github_username")
    private String githubUsername;

    @Column(length = 100, name = "usr_password")
    private String password;

    @Column(length = 20, name = "usr_role")
    private String role;

    @Column(length = 30, name = "usr_img")
    private String image;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Flashcard> flashcards;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Resume> resumes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Tag> tags;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Chat> chats;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User() {

    }

    public User(String name, String username, String password, String role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
