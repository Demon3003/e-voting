package com.team.app.backend.service.impl;

import com.team.app.backend.persistance.dao.AchievementDao;
import com.team.app.backend.persistance.model.Achievement;
import com.team.app.backend.persistance.model.UserAchievement;
import com.team.app.backend.service.AchievementService;
import com.team.app.backend.service.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AchievementServiceImpl implements AchievementService {

    @Autowired
    private AchievementDao achievementDao;

    @Autowired
    private UserActivityService userActivityService;

    @Override
    public List<UserAchievement> getUserAchievements(long id) {
        return achievementDao.getUserAchievements(id);
    }

    @Override
    public List<Achievement> getAchievements() {
        return achievementDao.getAchievements();
    }

    @Override
    public void createAchievement(Achievement achievement) {
        achievementDao.createAchievement(achievement);
    }

    @Override
    public void setUserAchievement(long userId) {
        achievementDao.setUserAchievement(userId);
    }

}
