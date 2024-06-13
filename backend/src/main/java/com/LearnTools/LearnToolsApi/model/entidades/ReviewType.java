package com.LearnTools.LearnToolsApi.model.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "review_type")
public class ReviewType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rev_id")
    private Integer id;

    @Column(name = "rev_type", nullable = false)
    private String type;

    @Column(name = "rev_days", nullable = false)
    private String days;
}
