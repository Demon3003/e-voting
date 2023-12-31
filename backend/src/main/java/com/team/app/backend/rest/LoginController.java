package com.team.app.backend.rest;

import com.team.app.backend.dto.RecoveryDto;
import com.team.app.backend.dto.UserLoginDto;
import com.team.app.backend.exception.DisabledUserException;
import com.team.app.backend.exception.NotActivatedUserException;
import com.team.app.backend.persistance.model.User;
import com.team.app.backend.security.jwt.JwtTokenProvider;
import com.team.app.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    MessageSource messageSource;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDto requestDto) {
        Map<String, String> model = new HashMap<String, String>();
        try {
            String username = requestDto.getUsername();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);
            String[] params = new String[]{username};
            if (user == null) {
                throw new UsernameNotFoundException(messageSource.getMessage("user.notfound", params, LocaleContextHolder.getLocale()));
            }
            if(user.getStatus().getId()==1){
                throw new NotActivatedUserException(messageSource.getMessage("user.not.active", null, LocaleContextHolder.getLocale()));
            }
            if(user.getStatus().getId()==3){
                throw new DisabledUserException(messageSource.getMessage("user.deactive", null, LocaleContextHolder.getLocale()));
            }
            String token = jwtTokenProvider.createToken(user);

            Map<Object, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());
            response.put("email", user.getEmail());
            response.put("password", user.getPassword());
            response.put("image", user.getImage());

            response.put("status", user.getStatus());
            response.put("role", user.getRole());

            response.put("username", username);
            response.put("token", token);
            
            return ResponseEntity.ok().body(response);

        } catch (AuthenticationException e) {
            model.put("message",messageSource.getMessage("user.invalid", null, LocaleContextHolder.getLocale()));
            return ResponseEntity
                    .badRequest()
                    .body(model);
        } catch (DisabledUserException | NotActivatedUserException e) {
            e.printStackTrace();
            model.put("message",messageSource.getMessage("user.noactive", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.badRequest()
                    .body(model);
        }
    }




    @PostMapping("/recovery")
    @ResponseBody

    public ResponseEntity recovery(@RequestBody RecoveryDto recoveryDto){
        String email = recoveryDto.getEmail();

        if(userService.isEmailRegistered(email)){
            userService.sendRecoveryLetter(email);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("No such email");
    }
    //TO DO
    @PostMapping("/logout")
    public Map<String, Object> logout() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", "Hello World");
        return model;
    }


}
