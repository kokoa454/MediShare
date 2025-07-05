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

        String userLineId = userService.getUserLineId(userId);
        if(userLineId == null) userLineId = "登録されていません";
        model.addAttribute("userLineId", userLineId);

        String familyLineId = userService.getFamilyLineId(userId);
        if(familyLineId == null) familyLineId = "登録されていません";
        model.addAttribute("familyLineId", familyLineId);

        String wakeUpTime = userService.getWakeUpTime(userId);
        String beforeBreakfastTime = userService.getBeforeBreakfastTime(userId);
        String afterBreakfastTime = userService.getAfterBreakfastTime(userId);
        String beforeLunchTime = userService.getBeforeLunchTime(userId);
        String afterLunchTime = userService.getAfterLunchTime(userId);
        String beforeDinnerTime = userService.getBeforeDinnerTime(userId);
        String afterDinnerTime = userService.getAfterDinnerTime(userId);
        String beforeSleepTime = userService.getBeforeSleepTime(userId);
        String betweenMealsTime = userService.getBetweenMealsTime(userId);

        model.addAttribute("wakeUpTime", wakeUpTime);
        model.addAttribute("beforeBreakfastTime", beforeBreakfastTime);
        model.addAttribute("afterBreakfastTime", afterBreakfastTime);
        model.addAttribute("beforeLunchTime", beforeLunchTime);
        model.addAttribute("afterLunchTime", afterLunchTime);
        model.addAttribute("beforeDinnerTime", beforeDinnerTime);
        model.addAttribute("afterDinnerTime", afterDinnerTime);
        model.addAttribute("beforeSleepTime", beforeSleepTime);
        model.addAttribute("betweenMealsTime", betweenMealsTime);
        return "settings";
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

    @PostMapping("/userLineId")
    public ResponseEntity<String> updateUserLineId(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        try{
            String userLineId = body.get("confirmedUserLineId");
            userService.updateUserLineId(userId, userLineId);
            return ResponseEntity.ok("ユーザーラインIDを更新しました");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ユーザーラインIDの更新に失敗しました");
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

    @PostMapping("/wakeUpTime")
    public ResponseEntity<String> updateWakeUpTime(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        try{
            String wakeUpTime = body.get("confirmedWakeUpTime");
            userService.updateWakeUpTime(userId, wakeUpTime);
            return ResponseEntity.ok("起床時間を更新しました");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("起床時間の更新に失敗しました");
        }
    }

    @PostMapping("/beforeBreakfastTime")
    public ResponseEntity<String> updateBeforeBreakfastTime(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        try {
            String time = body.get("confirmedBeforeBreakfastTime");
            userService.updateBeforeBreakfastTime(userId, time);
            return ResponseEntity.ok("朝食前時間を更新しました");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("朝食前時間の更新に失敗しました");
        }
    }

    @PostMapping("/afterBreakfastTime")
    public ResponseEntity<String> updateAfterBreakfastTime(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        try {
            String time = body.get("confirmedAfterBreakfastTime");
            userService.updateAfterBreakfastTime(userId, time);
            return ResponseEntity.ok("朝食後時間を更新しました");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("朝食後時間の更新に失敗しました");
        }
    }

    @PostMapping("/beforeLunchTime")
    public ResponseEntity<String> updateBeforeLunchTime(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        try {
            String time = body.get("confirmedBeforeLunchTime");
            userService.updateBeforeLunchTime(userId, time);
            return ResponseEntity.ok("昼食前時間を更新しました");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("昼食前時間の更新に失敗しました");
        }
    }

    @PostMapping("/afterLunchTime")
    public ResponseEntity<String> updateAfterLunchTime(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        try {
            String time = body.get("confirmedAfterLunchTime");
            userService.updateAfterLunchTime(userId, time);
            return ResponseEntity.ok("昼食後時間を更新しました");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("昼食後時間の更新に失敗しました");
        }
    }

    @PostMapping("/beforeDinnerTime")
    public ResponseEntity<String> updateBeforeDinnerTime(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        try {
            String time = body.get("confirmedBeforeDinnerTime");
            userService.updateBeforeDinnerTime(userId, time);
            return ResponseEntity.ok("夕食前時間を更新しました");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("夕食前時間の更新に失敗しました");
        }
    }

    @PostMapping("/afterDinnerTime")
    public ResponseEntity<String> updateAfterDinnerTime(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        try {
            String time = body.get("confirmedAfterDinnerTime");
            userService.updateAfterDinnerTime(userId, time);
            return ResponseEntity.ok("夕食後時間を更新しました");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("夕食後時間の更新に失敗しました");
        }
    }

    @PostMapping("/beforeSleepTime")
    public ResponseEntity<String> updateBeforeSleepTime(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        try {
            String time = body.get("confirmedBeforeSleepTime");
            userService.updateBeforeSleepTime(userId, time);
            return ResponseEntity.ok("就寝前時間を更新しました");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("就寝前時間の更新に失敗しました");
        }
    }

    @PostMapping("/betweenMealsTime")
    public ResponseEntity<String> updateBetweenMealsTime(@RequestBody Map<String, String> body) {
        int userId = userService.getLoginUserId();
        try {
            String time = body.get("confirmedBetweenMealsTime");
            userService.updateBetweenMealsTime(userId, time);
            return ResponseEntity.ok("食間時間を更新しました");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("食間時間の更新に失敗しました");
        }
    }
}
