package com.team.app.backend.service.impl;

import com.team.app.backend.persistance.dao.FormDao;
import com.team.app.backend.persistance.model.Form;
import com.team.app.backend.persistance.model.Notification;
import com.team.app.backend.service.FormService;
import com.team.app.backend.service.NotificationService;
import com.team.app.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;


@Service
public class FormServiceImpl implements FormService {

    private final long FORM_CREATE = 1L;

    private final long FORM_APPROVED = 2L;

    private final long NOTIFICATION_FORM = 1L;

    @Autowired
    private FormDao formDao;


    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @Autowired
    MessageSource messageSource;

    @Transactional
    public void createForm(Form form) {
        form.setCategoryId(FORM_CREATE);
        form.setDate(new Timestamp(System.currentTimeMillis()));
        formDao.create(form);
    }

    @Override
    public List<Form> getCreated() {

        return formDao.getCreated();
    }

    @Override
    public List<Form> getAll(Long userId) {

        return formDao.getAll(userId);
    }

    @Transactional
    @Override
    public void approve(Form form) {
        Notification notification = new Notification();
        notification.setCategoryId(NOTIFICATION_FORM);
        notification.setUserId(form.getUserId());
        String[] params = new String[]{form.getTitle()};
        if(form.getStatusId() == FORM_APPROVED) {
            formDao.approve(form.getId());
            notification.setText(messageSource.
                    getMessage("form.approved", params,
                            userService.getUserLanguage(form.getUserId())));
        } else {
            notification.setText(messageSource.
                    getMessage("form.not.approved", params,
                            userService.getUserLanguage(form.getUserId())));
            formDao.delete(form.getId());
        }
        notificationService.create(notification);
    }

    @Transactional
    public void updateForm(Form form) {

        formDao.update(form);
    }

    @Transactional
    public void deleteForm(Long id) {

        formDao.delete(id);
    }

}
