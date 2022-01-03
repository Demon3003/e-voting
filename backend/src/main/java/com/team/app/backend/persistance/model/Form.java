package com.team.app.backend.persistance.model;

import java.sql.Timestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Form extends BaseForm { 
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String title;
    private String image;
    private String text;
    private Long statusId;
    @Column(name = "user_id")
    private Long userId;
    public Form(Long id, String title, String text, Timestamp date, String image, Long statusId, Long categoryId, Long userId) {
        super( id,  text, date, categoryId, userId);
        this.title = title;
        this.image = image;
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", text='" + text + '\'' +
                ", statusId=" + statusId +
                '}';
    }
}
