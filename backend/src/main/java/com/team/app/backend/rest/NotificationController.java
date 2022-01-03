package com.team.app.backend.rest;

import com.team.app.backend.persistance.model.Notification;
import com.team.app.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/notification")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @MessageMapping("/get/notifications")
    public void getAll(Long userId, StompHeaderAccessor stompHeaderAccessor){
        notificationService.add(stompHeaderAccessor.getSessionId(), userId);
        notificationService.dispatch(stompHeaderAccessor.getSessionId());
    }

    @MessageMapping("/delete/notifications")
    public void delete(List<Notification> notifications) {
        notificationService.delete(notifications);
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Notification not) {
        notificationService.create(not);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Notification not) {
        notificationService.update(not);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/settings/get/{id}")
    public  ResponseEntity getSetting(@PathVariable("id") Long userId) {
        List<Notification> notifications = null;
        notifications = this.notificationService.getSetting(userId);
        return ResponseEntity.ok(notifications);
    }
    @PostMapping("/settings")
    public ResponseEntity setSetting(@RequestBody Notification not) {
        this.notificationService.setSetting(not);
        return ResponseEntity.ok().build();
    }


}
