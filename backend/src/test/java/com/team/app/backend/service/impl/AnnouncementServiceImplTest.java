package com.team.app.backend.service.impl;

import com.team.app.backend.persistance.dao.FormDao;
import com.team.app.backend.persistance.model.Form;
import com.team.app.backend.service.FormService;
import com.team.app.backend.service.NotificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.team.app.backend.persistance.model.Notification;
import com.team.app.backend.persistance.repositories.FormRepo;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;


@SpringBootTest
@RunWith(SpringRunner.class)
public class AnnouncementServiceImplTest {
    @Autowired
    FormService formService;

    @MockBean
    NotificationService notificationService;

    @Autowired
    FormRepo formRepo;

    @MockBean
    FormDao formDao;

    @Test
    public void createAnnouncement() {
//        doNothing().when(announcementDao).create(any(Announcement.class));
//        Announcement ann =  new Announcement();
//        ann.setStatusId(1L);
//        ann.setTitle("LolKaKaKa");
//        ann.setText("dscdscsdc");
//        ann.setUserId(165l);
//        announcementService.createAnnouncement(ann);
//        verify(announcementDao).create(ann);
    }

    @Test
    public void getCreated() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void approve() {
//        Announcement ann =  new Announcement();
//        ann.setStatusId(2l);
//        ann.setId(71L);
//        ann.setTitle("LolKaKaKa");
//        ann.setText("dscdscsdc");
//        ann.setUserId(72l);
//        announcementService.approve(ann);
//        List<Announcement> ans = announcementRepo.getAnnouncementByStatusId(1l);
//        assertThat(ann).isNotIn(ans);
//        verify(notificationService).create(any(Notification.class));
//        verify(announcementDao).approve(anyLong());
    }

    @Test
    public void deleteAnnouncement() {
    }
}
