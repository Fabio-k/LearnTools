package com.LearnTools.LearnToolsApi.controller.dto;

import java.util.List;

import com.LearnTools.LearnToolsApi.model.entidades.Flashcard;
import com.LearnTools.LearnToolsApi.model.entidades.Resume;

public class UserInformation {
    private String username;
    private Integer numResumes;
    private List<Resume> resumes;
    private Integer numFlashcards;
    private List<Flashcard> flashcards;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNumResumes() {
        return numResumes;
    }

    public void setNumResumes(Integer numResumes) {
        this.numResumes = numResumes;
    }

    public List<Resume> getResumes() {
        return resumes;
    }

    public void setResumes(List<Resume> resumes) {
        this.resumes = resumes;
    }

    public Integer getNumFlashcards() {
        return numFlashcards;
    }

    public void setNumFlashcards(Integer numFlashcards) {
        this.numFlashcards = numFlashcards;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

}
