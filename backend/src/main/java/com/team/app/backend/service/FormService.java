package com.team.app.backend.service;

import com.team.app.backend.persistance.model.Form;

import java.util.List;

public interface FormService {

    void createForm(Form form);
    void updateForm(Form form);
    void deleteForm(Long id);
    List<Form> getAll(Long userId);
    List<Form> getCreated();
    void approve(Form form);
}
