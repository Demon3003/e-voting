package com.team.app.backend.persistance.repositories;

import com.team.app.backend.persistance.model.District;
import com.team.app.backend.persistance.model.Election;
import com.team.app.backend.persistance.model.Role;
import com.team.app.backend.persistance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    @Query("select u.role from User u where u.id = :id")
    Role getUserRoleById(@Param("id") Long id);

    List<User> findByFirstName(String firstName);

    List<User> getUserByStatusId(Long id);

    @Query("select u.image  from User u where u.id = :id")
    String getUserImage(@Param("id")Long id);

    @Query("select u.elections from User u where u.id = :id")
    List<Election> getUserElections(@Param("id") Long id);

    @Query("select u.district from User u where u.id = :id")
    District getUserDistrictById(@Param("id")Long id);

    @Query("select u from User u where u.role.id = 5 and :el member of u.elections")
    List<User> getCandidatesByElectionId(@Param("el")Election el);

}
