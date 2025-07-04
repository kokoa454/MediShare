package com.medishare.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medishare.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SettingsController {
    private final UserService userService;

    @GetMapping
    public String settingsPage(Model model) {
        int userId = userService.getLoginUserId();

        String familyEmail = userService.getFamilyEmail(userId);
        if(familyEmail == null) familyEmail = "登録されていません";
        model.addAttribute("familyEmail", familyEmail);
        
        String userName = userService.getUserName(userId);
        if(userName == null) userName = "登録されていません";
        model.addAttribute("userName", userName);

        String familyLineId = userService.getFamilyLineId(userId);
        if(familyLineId == null) familyLineId = "登録されていません";
        model.addAttribute("familyLineId", familyLineId);
        return "settings";  // settings.html を表示
    }

    @PostMapping("/familyEmail")
    public ResponseEntity<String> updateFamilyEmail(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        try {
            String familyEmail = body.get("confirmedFamilyEmail");
            userService.updateFamilyEmail(userId, familyEmail);
            return ResponseEntity.ok("家族メールアドレスを更新しました");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("家族メールアドレスの更新に失敗しました");
        }
    }

    @PostMapping("/userEmail")
    public ResponseEntity<String> updateUserEmail(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        try {
            String userEmail = body.get("confirmedUserEmail");
            userService.updateUserEmail(userId, userEmail);
            return ResponseEntity.ok("ユーザーメールアドレスを更新しました");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ユーザーメールアドレスの更新に失敗しました");
        }
    }

    @PostMapping("/userName")
    public ResponseEntity<String> updateUserName(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        try {
            String userName = body.get("confirmedUserName");
            userService.updateUserName(userId, userName);
            return ResponseEntity.ok("ユーザー名を更新しました");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ユーザー名の更新に失敗しました");
        }
    }

    @PostMapping("/familyLineId")
    public ResponseEntity<String> updateFamilyLineId(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        try{
            String familyLineId = body.get("confirmedFamilyLineId");
            userService.updateFamilyLineId(userId, familyLineId);
            return ResponseEntity.ok("家族LINEIDを更新しました");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("家族LINEIDの更新に失敗しました");
        }
    }
}
