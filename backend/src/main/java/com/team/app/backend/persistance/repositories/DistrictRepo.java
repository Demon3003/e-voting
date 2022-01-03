package com.team.app.backend.persistance.repositories;

import com.team.app.backend.persistance.model.District;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepo extends JpaRepository<District, Long> {

    
}
