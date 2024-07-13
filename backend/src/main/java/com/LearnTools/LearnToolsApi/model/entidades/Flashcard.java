package com.LearnTools.LearnToolsApi.model.entidades;

import java.util.ArrayList;
import java.util.Date;
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
public class Flashcard {
    @Id
    @Column(name = "fls_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "flashcard", cascade = CascadeType.REMOVE)
    private List<FlashCardTag> flashCardTags = new ArrayList<>();

    @Column(length = 100, name = "fls_que")
    private String question;

    @Column(length = 300, name = "fls_ans")
    private String answer;

    @Column(name = "fls_priority")
    private Integer priority;

    @Column(name = "fls_revision_date")
    private Date revision_date;

    @Column(name = "fls_creation_date")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "fls_usr_id")
    private User user;

    public Flashcard() {

    }

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public List<FlashCardTag> getFlashCardTags() {
        return flashCardTags;
    }

    public void setFlashCardTags(List<FlashCardTag> flashCardTags) {
        this.flashCardTags = flashCardTags;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
