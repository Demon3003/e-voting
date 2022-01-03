package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.AchievementDao;
import com.team.app.backend.persistance.dao.mappers.AchievementRowMapper;
import com.team.app.backend.persistance.model.Achievement;
import com.team.app.backend.persistance.model.UserAchievement;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class AchievementDaoImpl implements AchievementDao {

    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    private Environment env;

    private AchievementRowMapper achievementRowMapper = new AchievementRowMapper();

    public AchievementDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<UserAchievement> getUserAchievements(long userId) {
        @NonNull String sqlGetUserAchievements = env.getProperty("achievement.user");
        return jdbcTemplate.query(sqlGetUserAchievements, new Object[] { userId },
                (resultSet, i) -> {
                    UserAchievement userAchievement = new UserAchievement();
                    userAchievement.setTitle(resultSet.getString("title"));
                    userAchievement.setQuizAmount(resultSet.getLong("quiz_amount"));
                    userAchievement.setPlayed(resultSet.getLong("played"));
                    return userAchievement;
                });
    }

    @Override
    public List<Achievement> getAchievements() {
        @NonNull String sqlGetAchievements= env.getProperty("achievement.all");;
        return jdbcTemplate.query(sqlGetAchievements, achievementRowMapper);
    }

    @Override
    public void createAchievement(Achievement achievement) {
        @NonNull String sqlCreateAchievement = env.getProperty("achievement.create");
        jdbcTemplate.update(sqlCreateAchievement,
                achievement.getTitle(),
                achievement.getIcon(),
                achievement.getAmountOfQuizzes(),
                achievement.getAmountOfCreated(),
                achievement.getCreatorUserId(),
                achievement.getCategoryId());
    }

    @Override
    public void setUserAchievement(long userId) {
        @NonNull String sqlSetUserAchievement = env.getProperty("achievement.set");
        jdbcTemplate.update(sqlSetUserAchievement, userId, userId);
    }

}
