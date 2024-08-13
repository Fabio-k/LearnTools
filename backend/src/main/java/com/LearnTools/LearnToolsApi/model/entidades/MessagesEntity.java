package com.LearnTools.LearnToolsApi.model.entidades;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class MessagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "msg_cht_id")
    private Chat chat;

    @Column(name = "msg_message", nullable = false)
    private String message;

    @Column(name = "msg_origin", nullable = false)
    private String origin;

    @Column(name = "msg_time", nullable = false)
    private LocalDateTime timestamp;

    public MessagesEntity() {

    }

    public MessagesEntity(Chat chat, String message, String origin, LocalDateTime timestamp) {
        this.chat = chat;
        this.message = message;
        this.origin = origin;
        this.timestamp = timestamp;
    }

    public MessagesEntity(LocalDateTime time, String role, String message) {
        this.timestamp = time;
        this.origin = role;
        this.message = message;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
