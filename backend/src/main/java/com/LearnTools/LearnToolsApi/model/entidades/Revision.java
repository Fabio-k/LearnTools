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
@Table(name = "flashcard_revision")
public class Revision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flr_id")
    private Integer id;

   @ManyToOne
   @JoinColumn(name = "flr_fls_id")
   private Flashcard flashcard;

    @ManyToOne
    @JoinColumn(name = "flr_rvd_id")
    private RevisionDificulty revisionDificulty;
}
