package com.team.app.backend.rest;

import com.team.app.backend.persistance.model.Form;
import com.team.app.backend.persistance.repositories.FormRepo;
import com.team.app.backend.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/form")
@Slf4j
public class FormController {

    @Autowired
    FormService formService;

    @Autowired
    FormRepo formRepo;

    @Autowired
    MessageSource messageSource;

    @PostMapping("/create")
    public ResponseEntity createForm(@RequestBody Form form) {
        Map<String, String> response = new HashMap<>();
        formService.createForm(form);
        response.put("message",messageSource
                .getMessage("form.success", null, LocaleContextHolder.getLocale()));
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/created")
    public ResponseEntity getCreated() {
        List<Form> formList;
        formList = formService.getCreated();
        return ResponseEntity.ok(formList);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity getAll(@PathVariable("id") Long userId) {
        List<Form> formList;
            formList = formRepo.findAll();
            log.info("TEST DMZH: {}", formList);
        return ResponseEntity.ok(formList);
    }

    @PostMapping("/approve")
    public ResponseEntity approve(@RequestBody Form form) {
            formService.approve(form);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update")
    public ResponseEntity updateForm(@RequestBody Form form) {
        Map<String, String> model = new HashMap<String, String>();
        formService.updateForm(form);
        model.put("message", messageSource
                .getMessage("form.updated", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteForm(@PathVariable("id") long id) {
        Map<String, String> model = new HashMap<String, String>();
        formService.deleteForm(id);
        model.put("message",messageSource
                .getMessage("form.deleted", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(model);
    }
}
