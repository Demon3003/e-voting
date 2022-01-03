package com.team.app.backend.persistance.repositories;

import com.team.app.backend.persistance.model.Form;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepo extends JpaRepository<Form, Long> {

    @Modifying
    @Query(value = "UPDATE form set status_id = 2  where id = :id", nativeQuery = true)
    void approveForm(@Param("id") Long id);

    List<Form> getFormByStatusId(Long statusId);

}
