package com.LearnTools.LearnToolsApi.model.entidades;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @Column(length = 300, name = "res_text", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "res_usr_id")
    private User user;

    @OneToMany(mappedBy = "resume")
    private List<Chat> chats;

    /*
     * @OneToMany(mappedBy = "resume", cascade = CascadeType.REMOVE)
     * private List<ResumeTag> resumeTags;
     */

    public Resume() {

    }

    public Resume(String title, String resume) {
        this.title = title;
        description = resume;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*
     * public List<ResumeTag> getResumeTags() {
     * return resumeTags;
     * }
     * 
     * public void setResumeTags(List<ResumeTag> resumeTags) {
     * this.resumeTags = resumeTags;
     * }
     */

}
