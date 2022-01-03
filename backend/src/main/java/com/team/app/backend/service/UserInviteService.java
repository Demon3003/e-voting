package com.team.app.backend.service;

import com.team.app.backend.persistance.model.User;
import com.team.app.backend.persistance.model.UserInvite;

import java.util.List;

public interface UserInviteService {
    void sendUserInvite(UserInvite userInvite);
    void acceptUserInvite(Long id);
    List<UserInvite> getUserInvite(Long userId);
    void declineUserInvite(Long id);
    List<UserInvite> getFriendsList(Long userId);
    void deleteUserFromList(Long userId, Long deleteId);
    List<User> getAllPendingUsers();
}
