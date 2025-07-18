package com.medishare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medishare.model.PasswordResetToken;

@Repository
public interface  PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer>{
    Optional<PasswordResetToken> findByToken(String token);
}
