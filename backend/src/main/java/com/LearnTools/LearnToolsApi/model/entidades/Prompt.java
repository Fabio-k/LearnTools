package com.LearnTools.LearnToolsApi.model.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "prompt")
public class Prompt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prt_id")
    private Integer id;
    @Column(name = "prt_name", length = 50, nullable = false)
    private String name;
    @Column(name = "prt_prompt", length = 100, nullable = false)
    private String prompt;

    public Prompt() {
    }

    public Prompt(String name, String prompt) {
        this.name = name;
        this.prompt = prompt;
    }

    @Override
    public String toString() {
        return "Assistent [id=" + id + ", name=" + name + ", prompt=" + prompt + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String description) {
        this.prompt = description;
    }

}
