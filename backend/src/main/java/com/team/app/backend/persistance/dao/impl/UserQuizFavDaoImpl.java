package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.UserQuizFavDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class UserQuizFavDaoImpl implements UserQuizFavDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    Environment env;


    public UserQuizFavDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void makeFavorite(Long user_id, Long quiz_id) {
        jdbcTemplate.update(
                env.getProperty("create.favourite"),
                user_id, quiz_id);
    }

    @Override
    public void deleteFavorite(Long user_id, Long quiz_id) {
        jdbcTemplate.update(
                env.getProperty("delete.favourite"),
                user_id,quiz_id
        );
    }
}
