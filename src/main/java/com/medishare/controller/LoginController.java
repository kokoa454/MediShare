package com.medishare.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medishare.model.PASSWORD_RESET_TOKEN;
import com.medishare.model.USER_DATABASE;
import com.medishare.repository.PasswordResetTokenRepository;
import com.medishare.repository.UserRepository;
import com.medishare.service.MailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final MailService mailService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping
    public String loginPage() {
        return "login";
    }

    @PostMapping("/reset_password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");

        USER_DATABASE user = userRepository.findByUserEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            PASSWORD_RESET_TOKEN passwordResetToken = new PASSWORD_RESET_TOKEN(token, email, LocalDateTime.now().plusHours(1).toString());
            passwordResetTokenRepository.save(passwordResetToken);

            String resetLink = "http://localhost:8080/reset_password?token=" + token;
            mailService.sendPasswordResetEmail(email, resetLink);
            logger.info("Password reset email sent successfully: user ID={}, email={}, reset link={}", user.getUserId(), email, resetLink);
        } else {
            logger.warn("Failed to send password reset email, email address does not exist, email={}", email);
        }
        return ResponseEntity.ok("ご登録のメールアドレス宛にパスワードリセットメールを送信しました");
    }
}
