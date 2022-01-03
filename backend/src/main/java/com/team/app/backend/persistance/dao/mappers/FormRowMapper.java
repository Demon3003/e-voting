package com.team.app.backend.persistance.dao.mappers;

import com.team.app.backend.persistance.model.Form;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FormRowMapper implements RowMapper<Form> {

    @Override
    public Form mapRow(ResultSet resultSet, int rownumber) throws SQLException {
        return new Form(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("text"),
                resultSet.getTimestamp("date"),
                resultSet.getString("image"),
                resultSet.getLong("status_id"),
                resultSet.getLong("cat_id"),
                resultSet.getLong("user_id"));
    }
}
