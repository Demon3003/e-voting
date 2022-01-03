package com.team.app.backend.rest;


import com.team.app.backend.dto.UserCreateDto;
import com.team.app.backend.dto.UserUpdateDto;
import com.team.app.backend.persistance.model.User;
import com.team.app.backend.persistance.repositories.ElectionRepo;
import com.team.app.backend.persistance.repositories.UserRepo;
import com.team.app.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.env.Environment;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ElectionRepo electionRepo;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private Environment env;

    @GetMapping("/user/search/{name}/{first}/{last}")
    public List<User> searchUser(
            @PathVariable("name") String name,
            @PathVariable("first") int firstRole,
            @PathVariable("last") int lastRole) {

        return userService.searchUsers(name, firstRole, lastRole);
    }


    @PutMapping("/user/update")
    public User updateUser(
                           @RequestBody UserUpdateDto userUpdateDto) {

        return userService.updateUser(userUpdateDto);
    };

    @PutMapping("/user/updateNew")
    public User updateUserNew(@RequestBody User user) {
        log.info("TEST: {}", user);
       
        return userRepo.save(user);
    }


    @PostMapping("user/create")
    public User createUser(@RequestBody UserCreateDto userDto) {
        return userService.createNewUser(userDto);
    }

    @PostMapping("user/createNew")
    public User createUser_2(@RequestBody User user) {
        user.setRegistr_date(new Date());
        log.info("User from client: {}", user);
        return userRepo.save(user);
    }

    @GetMapping("user/activate")
    public ModelAndView activateUser(@RequestParam("token") String token){
        if(userService.activateUserAccount(token)) {
            return new ModelAndView("redirect:"
                + env.getProperty("application.url") 
                + "/#/login");
        }
            
        return new ModelAndView("redirect:" 
            + env.getProperty("application.url")
            + "/#/signup" );
    }


    @DeleteMapping("user/delete/{id}")
    public Map<String,Object> deleteUser(@PathVariable("id") long id){
        Map<String, Object> model = new HashMap<String, Object>();
        if(userService.deleteUser(id)){

            model.put("message", messageSource
                .getMessage("delete.user.success", null, LocaleContextHolder.getLocale()));
        }else{
            model.put("message", messageSource
                .getMessage("delete.user.bad", null, LocaleContextHolder.getLocale()));
        }

        return model;
    }

    @PostMapping("/user/status/{id}/{user}")
    public ResponseEntity setStatus(@PathVariable("id") Long statusId,
                                    @PathVariable("user") Long userId) {
        userService.setStatus(statusId,userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("user/image/{id}")
    public Map<String,Object> getUserImage(@PathVariable("id") long id) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("imgUrl", userRepo.getUserImage(id));
        return model;
    }

    @GetMapping("/user/allForElections/{id}")
    public List<User> getAllCandidatedForElection(@PathVariable("id") Long id) {
        return userRepo.getCandidatesByElectionId(electionRepo.getOne(id));
    }

}
