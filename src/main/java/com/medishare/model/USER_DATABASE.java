package com.medishare.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "user_database")
@Entity
@Getter
@Setter
public class USER_DATABASE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String familyEmail;

    public USER_DATABASE(){

    }
    public USER_DATABASE(String userEmail, String password, String familyEmail){
        this.userEmail = userEmail;
        this.password = password;
        this.familyEmail = familyEmail;
    }

}
