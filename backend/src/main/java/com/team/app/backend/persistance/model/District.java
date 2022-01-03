package com.team.app.backend.persistance.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import javax.persistence.JoinColumn;

import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private String city;

    private String address;

    @ManyToMany
    @JsonProperty(access = Access.WRITE_ONLY)
    @JoinTable(
        name = "election_to_district",
        joinColumns = @JoinColumn(name = "district_id"),
        inverseJoinColumns = @JoinColumn(name = "election_id")
    )
    private List<Election> elections;

    @OneToMany(mappedBy = "district")
    @JsonIgnore
    private List<User> users;

}
