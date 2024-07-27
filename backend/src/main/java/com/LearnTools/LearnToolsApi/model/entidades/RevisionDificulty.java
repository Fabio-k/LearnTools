package com.LearnTools.LearnToolsApi.model.entidades;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "revision_dificulty")
public class RevisionDificulty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rvd_id")
    private Integer id;

    @Column(name = "rvd_name")
    private String name;

    @OneToMany(mappedBy = "revisionDificulty")
    private List<Revision> revisions;
}
