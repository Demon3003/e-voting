package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.FormDao;
import com.team.app.backend.persistance.dao.mappers.FormRowMapper;
import com.team.app.backend.persistance.model.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FormDaoImpl implements FormDao {


    private JdbcTemplate jdbcTemplate;

    private Environment env;

    private FormRowMapper formRowMapper = new FormRowMapper();

    @Autowired
    public FormDaoImpl(@Qualifier("data") DataSource dataSource, Environment env) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.env = env;
    }


    @Override
    public void create(Form form) {
        jdbcTemplate.update(
                env.getProperty("create.form"),
                form.getTitle(),
                form.getText(),
                form.getDate(),
                form.getImage(),
                form.getStatusId(),
                form.getCategoryId(),
                form.getUserId()
        );
    }

    @Override
    public void update(Form form) {
        jdbcTemplate.update(
                env.getProperty("update.form"),
                form.getTitle(),
                form.getText(),
                form.getDate(),
                form.getImage(),
                form.getStatusId(),
                form.getCategoryId(),
                form.getUserId(),
                form.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                env.getProperty("delete.form"),
                id
        );
    }
    @Override
    public List<Form> getCreated() {

        return jdbcTemplate.query(env.getProperty("get.created.form")
                ,formRowMapper);

    }
    @Override
    public List<Form> getAll(Long userId) {
        return jdbcTemplate.query(env.getProperty("get.all.form"),
                formRowMapper);

    }
    @Override
    public void approve(Long id) {
        jdbcTemplate.update(env.getProperty("approve.form"), id);
    }


    @Override
    public Form get(Long id) {
        return jdbcTemplate.queryForObject(env.getProperty("get.form"),
                new Object[]{id},
                formRowMapper);
    }

}
