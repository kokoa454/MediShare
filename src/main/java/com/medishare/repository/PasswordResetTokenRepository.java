package com.medishare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medishare.model.PASSWORD_RESET_TOKEN;

@Repository
public interface  PasswordResetTokenRepository extends JpaRepository<PASSWORD_RESET_TOKEN, Integer>{
    Optional<PASSWORD_RESET_TOKEN> findByToken(String token);
}
