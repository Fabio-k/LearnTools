package com.LearnTools.LearnToolsApi.model.entidades;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mes_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cht_id")
    private Chat chat;

    @Column(name = "mes_message", nullable = false)
    private String message;

    @Column(name = "mes_source", nullable = false)
    private Boolean IsFromUser;

    @Column(name = "mes_time", nullable = false)
    private LocalDateTime timestamp;
}
