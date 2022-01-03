package com.team.app.backend.persistance.dao;

import com.team.app.backend.persistance.model.Form;

import java.util.List;

public interface FormDao {
    void create(Form form);
    Form  get(Long id);
    void update(Form form);
    void delete(Long id);
    List<Form> getCreated();
    List<Form> getAll(Long userId);
    void approve(Long id);
}
