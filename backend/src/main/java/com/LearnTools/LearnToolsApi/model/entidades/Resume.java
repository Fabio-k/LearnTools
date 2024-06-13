package com.LearnTools.LearnToolsApi.model.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "resume")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "res_id")
    private Integer id;

    @Column(length = 50, name = "res_title", nullable = false)
    private String title;

    @Column(length = 300, name = "res_des", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "usr_id")
    private User user;
}
