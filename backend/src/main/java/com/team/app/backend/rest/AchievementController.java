package com.team.app.backend.rest;


import com.team.app.backend.persistance.model.Achievement;
import com.team.app.backend.persistance.model.Notification;
import com.team.app.backend.persistance.model.UserAchievement;
import com.team.app.backend.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/achievement")
public class AchievementController {

    @Autowired
    AchievementService achievementService;
    @Autowired
    MessageSource messageSource;


    @GetMapping("/all")
    public ResponseEntity<List<Achievement>> getAchievements() {
        return ResponseEntity.ok().body(achievementService.getAchievements());
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<UserAchievement>> getUserAchievements(@PathVariable("user_id") long id) {
        return ResponseEntity.ok().body(achievementService.getUserAchievements(id));
    }

    @PostMapping("set/{user_id}")
    public ResponseEntity checkUserAchievements(@PathVariable("user_id") long id) {
        Map<String, String> response = new HashMap<>();
        achievementService.setUserAchievement(id);
        response.put("message", "Achievement was set!");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity createAchievement(@RequestBody Achievement achievement) {
        Map<String, String> response = new HashMap<>();
        achievementService.createAchievement(achievement);
        response.put("message", messageSource.getMessage("achievement.success", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(response);
    }

}
