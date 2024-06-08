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
@Table(name = "flashcard_tag")
public class FlashCardTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 50, name = "fst_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fst_fls_id")
    private Flashcard flashcard;

    @ManyToOne
    @JoinColumn(name = "fst_tag_id")
    private Tag tag;

    public FlashCardTag() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Flashcard getFlashcard() {
        return flashcard;
    }

    public void setFlashcard(Flashcard flashcard) {
        this.flashcard = flashcard;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

}
