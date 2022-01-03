package com.team.app.backend.persistance.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_status")
public class UserStatus {
    @Id
    private Long id;
    private String name;
}
