package com.medishare.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class USER_DATABASE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(nullable = false, unique = true)
    private String user_email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String family_email;

}
