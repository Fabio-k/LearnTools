package com.LearnTools.LearnToolsApi.model.entidades;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Cascade;

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

    @Column(length = 30, name = "cht_assistent_name")
    private String assistentName;

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

    public Chat(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
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

    public void setUser(User user) {
        this.user = user;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public String getAssistentName() {
        return assistentName;
    }

    public void setAssistentName(String assistentName) {
        this.assistentName = assistentName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
