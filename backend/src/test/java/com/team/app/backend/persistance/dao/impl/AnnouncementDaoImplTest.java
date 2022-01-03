package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.FormDao;
import com.team.app.backend.persistance.model.Form;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class AnnouncementDaoImplTest {

    @Autowired
    FormDao formDao;

    @Test
    public void create() {
//        Announcement ann =  new Announcement();
//        ann.setStatusId(1L);
//        ann.setCategoryId(1l);
//        ann.setTitle("Lol");
//        ann.setText("dscdscsdc");
//        ann.setDate(new Timestamp(100l));
//        ann.setUserId(28l);
//        announcementDao.create(ann);
//        assertThat(announcementDao.get(72l).getTitle()).isEqualTo(ann.getTitle());
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getCreated() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void approve() {
    }

    @Test
    public void get_If_Present() {

    }
}
