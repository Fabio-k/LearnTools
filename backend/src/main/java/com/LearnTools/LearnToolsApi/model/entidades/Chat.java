package com.LearnTools.LearnToolsApi.model.entidades;

import java.util.ArrayList;
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
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cht_id")
    private Integer id;

    @Column(name = "cht_title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "cht_usr_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cht_res_id")
    private Resume resume;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE)
    private List<MessagesEntity> messages = new ArrayList<>();

    public Chat() {

    }

    public Chat(String title, User user) {
        this.title = title;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    public List<MessagesEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessagesEntity> messages) {
        this.messages = messages;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

}
