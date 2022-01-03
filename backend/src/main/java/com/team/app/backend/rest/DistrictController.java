package com.team.app.backend.rest;

import java.util.Arrays;
import java.util.List;

import com.team.app.backend.persistance.model.District;
import com.team.app.backend.persistance.repositories.DistrictRepo;
import com.team.app.backend.persistance.repositories.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/district/")
public class DistrictController {

    @Autowired
    private DistrictRepo districtRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("all")
    public ResponseEntity<List<District>> getAllDistricts() {
        return ResponseEntity.ok(districtRepo.findAll());

    }

    @GetMapping("get/{id}")
    public ResponseEntity getDistrict(@PathVariable("id") Long id) {
        
        return ResponseEntity.ok(districtRepo.findById(id));
    }

    @GetMapping("getForUser/{id}")
    public ResponseEntity getDistrictForUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userRepo.getUserDistrictById(id));
    }

    @PostMapping("create")
    public ResponseEntity createDistrict(@RequestBody District district) {

        return ResponseEntity.ok(districtRepo.save(district));
    }

    @PutMapping("update")
    public ResponseEntity updateDistrict(@RequestBody District district) {
        return ResponseEntity.ok(districtRepo.save(district));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteDistrict(@PathVariable("id") Long id) {
        districtRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
