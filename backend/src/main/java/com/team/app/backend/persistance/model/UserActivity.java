package com.team.app.backend.persistance.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
public class UserActivity extends BaseForm{
    private String username;
    private Long elem_id;
    private String elem_name;
    private String image;

    public UserActivity() {
    }

    public UserActivity(Long id, String text, Timestamp date, Long categoryId, Long userId, Long elem_id,String username,String elem_name,String image) {
        super(id, text, date, categoryId, userId);
        this.elem_id = elem_id;
        this.username=username;
        this.elem_name=elem_name;
        this.image=image;
    }
}
