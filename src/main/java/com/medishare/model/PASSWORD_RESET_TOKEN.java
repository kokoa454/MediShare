package com.medishare.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "password_reset_token")
@Entity
@Getter
@Setter
public class PASSWORD_RESET_TOKEN {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reset_id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String expiryDate;

    public PASSWORD_RESET_TOKEN() {}

    public PASSWORD_RESET_TOKEN(String token, String email, String expiryDate) {
        this.token = token;
        this.email = email;
        this.expiryDate = expiryDate;
    }
}
