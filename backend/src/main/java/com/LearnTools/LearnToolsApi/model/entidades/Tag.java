package com.LearnTools.LearnToolsApi.model.entidades;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Integer id;

    @Column(name = "tag_name", length = 30)
    private String name;

    @Column(name = "tag_color", length = 9)
    private String color;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE)
    private List<FlashCardTag> flashCardTags;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE)
    private List<ResumeTag> resumeTags;

    @ManyToOne
    @JoinColumn(name = "tag_usr_id")
    private User user;

    public Tag() {
    }

    public Tag(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<FlashCardTag> getFlashCardTags() {
        return flashCardTags;
    }

    public void setFlashCardTags(List<FlashCardTag> flashCardTags) {
        this.flashCardTags = flashCardTags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
