package com.LearnTools.LearnToolsApi.model.entidades;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tab_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Integer id;

    @Column(length = 50, name = "usr_name")
    private String name;

    @Column(length = 50, name = "usr_username", nullable = false)
    private String username;

    @Column(length = 100, name = "usr_password", nullable = false)
    private String password;

    @Column(length = 30, name = "usr_img")
    private String image;

    @OneToMany(mappedBy = "user")
    private List<Flashcard> flashcards;

    @OneToMany(mappedBy = "user")
    private List<Resume> resumes;

    @OneToMany(mappedBy = "user")
    private List<Tag> tags;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tab_user_roles", joinColumns = @JoinColumn(name = "usr_id"))
    @Column(name = "role_id")
    private List<String> roles = new ArrayList<>();

    public User() {

    }

    public User(String username) {
        this.username = username;
    }

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
