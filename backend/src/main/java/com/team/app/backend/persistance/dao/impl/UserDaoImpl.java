package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.UserDao;
import com.team.app.backend.persistance.dao.mappers.UserRowMapper;
import com.team.app.backend.persistance.model.User;

import org.apache.tomcat.util.json.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Slf4j
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRowMapper userRowMapper;

    @Autowired
    Environment env;

    public UserDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update(
                "INSERT INTO users(firstname, lastname, username, password, email, image, registr_date, activate_link, status_id, role_id) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getImage(),
                user.getRegistr_date(),
                user.getActivate_link(),
                user.getStatus().getId(),
                user.getRole().getId()
        );
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(
                "UPDATE USERS set firstname = ? , lastname = ? , username = ? , password = ? , email = ? , image = ? , registr_date = ? , activate_link = ? , status_id = ? , role_id = ? where id = ?",
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getImage(),
                user.getRegistr_date(),
                user.getActivate_link(),
                user.getStatus().getId(),
                user.getRole().getId(),
                user.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                "DELETE from USERS where id = ?",
                id
        );
    }


    @Override
    public User get(Long id) {
        return jdbcTemplate.queryForObject(
                "select U.id,U.firstname,U.lastname,U.username,U.image,U.password,U.email,U.registr_date,U.activate_link,U.status_id,US.name as status_name,U.role_id,R.name as role_name from users U INNER JOIN user_status US ON U.status_id = US.id INNER JOIN role R ON R.id = U.role_id where U.id = ? ",
                new Object[]{id},
                userRowMapper);
    }

    @Override
    public User findByUsername(String username) {
        String sql="select U.id,U.firstname,U.lastname,U.username,U.image,U.password,U.email,U.registr_date,U.activate_link,U.status_id,US.name as status_name,U.role_id,R.name as role_name from users U INNER JOIN user_status US ON U.status_id = US.id INNER JOIN role R ON R.id = U.role_id where U.username = ? ";
        List<User> userslist=jdbcTemplate.query(sql,
                new Object[]{username},
                userRowMapper);
        if(userslist.size()==0){
            return null;
        }else{
            return userslist.get(0);
        }

    }

    @Override
    public String getUserPasswordByUsername(String username) {
        return jdbcTemplate.queryForObject(
                "SELECT PASSWORD FROM users WHERE USERNAME = ? ",
                new Object[]{username},String.class
        );
    }


    @Override
    public User getUserByToken(String token) {
        return jdbcTemplate.queryForObject(
                "select U.id,U.firstname,U.lastname,U.username,U.image,U.password,U.email,U.registr_date,U.activate_link,U.status_id,US.name as status_name,U.role_id,R.name as role_name from users U INNER JOIN user_status US ON U.status_id = US.id INNER JOIN role R ON R.id = U.role_id where U.activate_link = ? ",
                new Object[]{token},
                userRowMapper);    }



    @Override
    public void activateByToken(String token) {
        jdbcTemplate.update(
                "UPDATE users set status_id = 4 WHERE activate_link = ?",
                token
        );
    }

    @Override
    public boolean checkTokenAvailability(String token) {
        log.info("Token: {}", token);
        log.info("JDBC: {}", jdbcTemplate);
        return jdbcTemplate.queryForObject(
                "SELECT ? IN (SELECT activate_link FROM users WHERE activate_link IS NOT null)",
                new Object[]{token}, Boolean.class
        );
    }

    @Override
    public boolean checkEmail(String email) {
        return jdbcTemplate.queryForObject(
                "SELECT ? IN (SELECT email FROM users)",
                new Object[]{email},Boolean.class
        );
    }

    @Override
    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(
                env.getProperty("get.user.by.email"),
                new Object[]{email},
                userRowMapper);
    }

    public void changeLanguage(Long langId , Long userId) {
        jdbcTemplate.update(env.getProperty("set.user.language"),
                langId, userId
        );
    }

    @Override
    public String getUserLanguage(Long id) {
        return jdbcTemplate.queryForObject(env.getProperty("get.user.language"),
                new Object[]{id},String.class
        );
    }

    @Override
    public List<User> searchByString(String searchstring, int firstRole, int lastRole) {
        String search="%"+searchstring+"%";
        String sql=env.getProperty("search.user");
        return jdbcTemplate.query(sql,new Object[]{search,search,search, firstRole, lastRole}, userRowMapper);
    }

    @Override
    public void setStatus(Long statusId, Long userId) {
        jdbcTemplate.update(env.getProperty("set.user.status"),
                statusId, userId
        );
    }
}
