package com.team.app.backend.persistance.model;

import lombok.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    public User(Long id, String firstName, String lastName, String username, String password, String email, String image, Date registr_date, String activate_link, UserStatus status, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.image = image;
        this.registr_date = registr_date;
        this.activate_link = activate_link;
        this.status = status;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    private String username;
    private String password;
    private String email;
    private String image;
    private Date registr_date;
    private String activate_link;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    List<Form> form;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private UserStatus status;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany
    @JoinTable(
        name = "user_to_election", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "election_id"))
    @JsonProperty(access = Access.WRITE_ONLY)
    private Set<Election> elections;

    @ManyToOne
    @JoinTable(
        name ="user_to_district",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "district_id")
    )
    @JsonProperty(access = Access.WRITE_ONLY)
    private District district;

    private String passport;

}