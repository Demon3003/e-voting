package com.team.app.backend.rest;

import com.team.app.backend.dto.ResetPasswordDto;
import com.team.app.backend.dto.UserUpdateDto;
import com.team.app.backend.exception.NotMatchingPasswordsException;
import com.team.app.backend.exception.UserNotFoundException;
import com.team.app.backend.persistance.model.User;
import com.team.app.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ResetPasswordController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MessageSource messageSource;

    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordDto resetPasswordDto)
            throws NotMatchingPasswordsException, UserNotFoundException {

        User user = userService.findByUsername(resetPasswordDto.getUsername());

        if (user == null) {
            String[] params = new String[]{resetPasswordDto.getUsername()};
            throw new UserNotFoundException(messageSource.getMessage("user.notexist", params, LocaleContextHolder.getLocale()));
        }

        if (!passwordEncoder.matches(resetPasswordDto.getOldPassword(), user.getPassword())) {
            throw new NotMatchingPasswordsException(
                    messageSource.getMessage("password.not.match", null, LocaleContextHolder.getLocale())
            );
        }

        user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));

        UserUpdateDto userUpdateDto = new UserUpdateDto(user);

        userService.updateUser(userUpdateDto);

        return ResponseEntity.ok(messageSource.getMessage("password.reset", null, LocaleContextHolder.getLocale()));

    }

}
