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

    public USER_DATABASE(){

    }
    public USER_DATABASE(String user_email, String password, String family_email){
        this.user_email = user_email;
        this.password = password;
        this.family_email = family_email;
    }

}
