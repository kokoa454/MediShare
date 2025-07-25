package com.medishare.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medishare.model.PasswordResetToken;
import com.medishare.model.User;
import com.medishare.repository.PasswordResetTokenRepository;
import com.medishare.service.UserService;
import com.medishare.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reset_password")
@RequiredArgsConstructor
public class ResetPasswordController {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);

    @GetMapping
    public String resetPassword_page(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset_password";  // reset_password.html を表示
    }

    @PostMapping
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> body) {
        try {
            String token = body.get("token");
            String password = body.get("password");
            Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(token);

            if(passwordResetToken.isPresent() && 
                java.time.LocalDateTime.parse(passwordResetToken.get().getExpiryDate())
                    .isAfter(java.time.LocalDateTime.now())) {
                PasswordResetToken passwordResetTokenEntity = passwordResetToken.get();

                String userEmail = passwordResetTokenEntity.getEmail();
                User user = userRepository.findByUserEmail(userEmail);
                String encodedPassword = passwordEncoder.encode(password);
                user.setPassword(encodedPassword);
                userRepository.save(user);
                passwordResetTokenRepository.delete(passwordResetTokenEntity);
                logger.info("パスワード更新成功: ユーザID={}, メールアドレス={}", user.getUserId(), userEmail);
                return ResponseEntity.ok("パスワードを更新しました");
            } else {
                return ResponseEntity.status(400).body("無効なトークンです");
            }
        } catch (Exception e) {
            logger.error("パスワード更新失敗: ユーザID={}, エラー={}", userService.getLoginUserId(), e.getMessage());
            return ResponseEntity.status(500).body("パスワードの更新に失敗しました");
        }
    }
}