package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.medishare.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.Map;

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

        return "settings";  // settings.html を表示
    }

    @PostMapping("/familyEmail")
    public String updateFamilyEmail(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        String familyEmail = body.get("confirmedFamilyEmail");
        userService.updateFamilyEmail(userId, familyEmail);
        return "redirect:/settings";
    }

    @PostMapping("/userEmail")
    public String updateUserEmail(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        String userEmail = body.get("confirmedUserEmail");
        userService.updateUserEmail(userId, userEmail);
        return "redirect:/settings";
    }
}
