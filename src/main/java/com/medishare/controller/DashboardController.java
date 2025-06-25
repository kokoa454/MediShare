package com.medishare.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medishare.service.MedicineService;
import com.medishare.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.ui.Model;
import com.medishare.dto.TimingGroupDTO;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {
    private final MedicineService medicineService;
    private final UserService userService;

    @GetMapping
    public String dashboardPage(Model model) {
        int userId = userService.getLoginUserId(); // ログインユーザーのIDを取得

        List<TimingGroupDTO> timingMedicines = medicineService.getTimingMedicinesByUser(userId); // ユーザーの時間帯の薬を取得してグループ化
        List<TimingGroupDTO> selectedTimeMedicines = medicineService.getSelectedTimeMedicinesByUser(userId); // ユーザーの時間指定の薬を取得してグループ化

        // モデルにデータを追加し、ビュー(Thymeleaf側)に渡す
        model.addAttribute("timingMedicines", timingMedicines);
        model.addAttribute("medicines", selectedTimeMedicines);

        return "dashboard";  // dashboard.html を表示
    }
}
