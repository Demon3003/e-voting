package com.team.app.backend.service.impl;

import com.team.app.backend.dto.FinishedQuizDto;
import com.team.app.backend.persistance.dao.*;
import com.team.app.backend.persistance.model.Session;
import com.team.app.backend.persistance.model.User;
import com.team.app.backend.persistance.model.UserActivity;
import com.team.app.backend.persistance.model.UserToSession;
import com.team.app.backend.service.UserToSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserToSessionServiceImpl implements UserToSessionService {

    @Autowired
    UserToSessionDao userToSessionDao;

    @Autowired
    SessionDao sessionDao;

    @Autowired
    UserActivityDao userActivityDao;

    @Autowired
    QuizDao quizDao;

    @Autowired
    UserDao userDao;

    @Override
    public UserToSession createNewUserToSession(User user, Session session) {
        UserToSession userToSession = new UserToSession();
        userToSession.setUser_id(user.getId());
        userToSession.setSession_id(session.getId());
        return userToSessionDao.save(userToSession);
    }

    @Override
    public List<UserToSession> getAllBySessionId(Long sessionId) {
        return userToSessionDao.getAllBySes(sessionId);
    }

    @Override
    public void insertScore(FinishedQuizDto finishedQuizDto) {
        Session session=sessionDao.getById(finishedQuizDto.getSes_id());
        Long user_id =finishedQuizDto.getUser_id();

        UserToSession userToSession = new UserToSession();
        userToSession.setScore(finishedQuizDto.getScore());
        userToSession.setSession_id(session.getId());
        userToSession.setUser_id(user_id);
        userToSession.setTime(finishedQuizDto.getTime());


        UserActivity userActivity=new UserActivity();
        userActivity.setCategoryId(1L);
        userActivity.setDate(session.getDate());
        userActivity.setUserId(user_id);
        userActivity.setElem_id(session.getQuiz_id());
        userActivityDao.create(userActivity);
        userToSessionDao.update(userToSession);
    }
}
