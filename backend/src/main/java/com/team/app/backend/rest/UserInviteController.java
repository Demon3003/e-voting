package com.team.app.backend.rest;

import com.team.app.backend.persistance.model.User;
import com.team.app.backend.persistance.model.UserInvite;
import com.team.app.backend.service.UserInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/user/invite")
public class UserInviteController {

    @Autowired
    UserInviteService userInviteService;

    @Autowired
    MessageSource messageSource;

    @PostMapping("/send")
    public ResponseEntity sendUserInvite(@RequestBody UserInvite userInvite) {
        Map<String, String> response = new HashMap<>();
        userInviteService.sendUserInvite(userInvite);
        response.put("message", messageSource.getMessage("invite.create", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<UserInvite>> getUserInvite(@PathVariable("user_id") long id) {
        return ResponseEntity.ok().body(userInviteService.getUserInvite(id));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<User>> getPendingUsers() {
        return ResponseEntity.ok().body(userInviteService.getAllPendingUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity acceptUserInvite(@PathVariable("id") long id) {
        Map<String, String> model = new HashMap<>();
        userInviteService.acceptUserInvite(id);
        model.put("message", messageSource.getMessage("invite.updated", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity declineUserInvite(@PathVariable("id") long id) {
        Map<String, String> model = new HashMap<>();
        userInviteService.declineUserInvite(id);
        model.put("message", messageSource.getMessage("invite.decline", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(model);
    }

    @GetMapping("/friends/{user_id}")
    public ResponseEntity<List<UserInvite>> getFriends(@PathVariable("user_id") long id) {
        return ResponseEntity.ok().body(userInviteService.getFriendsList(id));
    }

    @DeleteMapping("/friends/{user_id}/{delete_id}")
    public ResponseEntity deleteUserFromList(@PathVariable("user_id") long id,
                                             @PathVariable("delete_id") long deleteId) {
        Map<String, String> model = new HashMap<>();
        userInviteService.deleteUserFromList(id, deleteId);
        model.put("message", messageSource.getMessage("form.deleted", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(model);
    }
}
