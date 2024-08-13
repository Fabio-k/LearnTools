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
@Table(name = "resume_category")
public class ResumeTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rsc_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "rsc_res_id")
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "rsc_tag_id")
    private Tag tag;

    public ResumeTag() {
    }

    public ResumeTag(Resume resume, Tag tag) {
        this.resume = resume;
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

}
