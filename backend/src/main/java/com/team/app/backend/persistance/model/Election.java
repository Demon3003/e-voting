package com.team.app.backend.persistance.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "created_by")
    private Long createdById;

    @ManyToMany(mappedBy = "elections", fetch = FetchType.EAGER)
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<User> users;

    @ManyToMany(mappedBy = "elections")
    private List<District> districts;

}
