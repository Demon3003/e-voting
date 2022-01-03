package com.team.app.backend.persistance.repositories;

import com.team.app.backend.persistance.model.Election;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepo extends JpaRepository<Election, Long> {

}
