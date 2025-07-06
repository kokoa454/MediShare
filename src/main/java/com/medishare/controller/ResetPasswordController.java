package com.medishare.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reset_password")
public class ResetPasswordController {
    @GetMapping
    public String resetPassword_page(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset_password";  // reset_password.html を表示
    }

    @PostMapping("/reset_password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok("家族メールアドレスを更新しました");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("家族メールアドレスの更新に失敗しました");
        }
    }
}