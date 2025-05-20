package com.medishare.service;

import com.medishare.model.USER_DATABASE;
import com.medishare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ユーザー登録処理
    public boolean registerUser(String userEmail, String password) {
        if (userRepository.existsByUserEmail(userEmail)) {
            return false;  // すでに登録済み
        }

        String encodedPassword = passwordEncoder.encode(password);
        USER_DATABASE user = new USER_DATABASE(userEmail, encodedPassword, null);
        userRepository.save(user);
        return true;
    }

    // ユーザー認証処理
    public boolean authenticateUser(String userEmail, String password) {
        USER_DATABASE user = userRepository.findByUserEmail(userEmail);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
}
