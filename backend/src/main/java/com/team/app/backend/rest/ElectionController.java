package com.team.app.backend.rest;

import java.util.List;

import com.team.app.backend.persistance.model.Election;
import com.team.app.backend.persistance.repositories.ElectionRepo;
import com.team.app.backend.persistance.repositories.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/election/")
public class ElectionController {

    @Autowired
    private ElectionRepo electionRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Environment env;

    @GetMapping("all")
    public ResponseEntity<List<Election>> getAllElections() {
        return ResponseEntity.ok(electionRepo.findAll());

    }

    @GetMapping("get/{id}")
    public ResponseEntity getElection(@PathVariable("id") Long id) {
        return ResponseEntity.ok(electionRepo.findById(id));
    }  

    @GetMapping("getForUser/{id}")
    public ResponseEntity getElectionsForUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userRepo.getUserElections(id));
    }  

    @PostMapping("create")
    public ResponseEntity createElection(@RequestBody Election election) {

        return ResponseEntity.ok(electionRepo.save(election));
    }

    @PutMapping("update")
    public ResponseEntity updateElection(@RequestBody Election election) {
        return ResponseEntity.ok(electionRepo.save(election));
    }    

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteElection(@PathVariable("id") Long id) {
        electionRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("voting/{userId}/{electionId}")
    public ModelAndView redirectToVoting(@PathVariable("userId") Long userId,
                                    @PathVariable("electionId") Long electionId) {

        return new ModelAndView("redirect:" 
            + env.getProperty("application.url")
            + String.format("/#/home/%s/%s", userId, electionId));
    }

    @PostMapping("vote/{userId}/{candidateId}")
    public ResponseEntity writeVoice(@PathVariable("userId") Long userId,
                                    @PathVariable("candidateId") Long candidateId) {

        jdbcTemplate.update(env.getProperty("add.voice"), userId, candidateId, candidateId);
                return null;
    }
    
}
