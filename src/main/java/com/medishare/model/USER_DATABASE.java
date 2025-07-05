package com.medishare.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @Column(nullable = true)
    private String userName;

    @Column(nullable = true)
    private String userLineId;

    @Column(nullable = true)
    private String familyLineId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<USER_MEDICINE> medicines;

    public USER_DATABASE(){

    }
    public USER_DATABASE(String userEmail, String password, String familyEmail, String userName, String userLineId, String familyLineId) {
        this.userEmail = userEmail;
        this.password = password;
        this.familyEmail = familyEmail;
        this.userName = userName;
        this.userLineId = userLineId;
        this.familyLineId = familyLineId;
    }

}
