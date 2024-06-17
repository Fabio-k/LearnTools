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
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cht_id")
    private Integer id;

    @Column(name = "cht_title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "usr_id")
    private User user;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE)
    private List<Messages> messages;

}
