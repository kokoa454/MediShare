package com.medishare.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medishare.dto.TimeZoneGroupDTO;
import com.medishare.model.TimeZoneCategory;
import com.medishare.service.UserMedicineService;
import com.medishare.service.UserService;

import lombok.RequiredArgsConstructor;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {
    private final UserMedicineService userMedicineService;
    private final UserService userService;

    @GetMapping
    public String dashboardPage(Model model) {
        int userId = userService.getLoginUserId(); // ログインユーザーのIDを取得

        List<TimeZoneGroupDTO> timingMedicines = userMedicineService.getTimingMedicinesByUser(userId); // ユーザーの時間帯の薬を取得してグループ化
        List<TimeZoneGroupDTO> selectedTimeMedicines = userMedicineService.getSelectedTimeMedicinesByUser(userId); // ユーザーの時間指定の薬を取得してグループ化

        // 各グループの薬の有無を判定
        Map<String, TimeZoneGroupDTO> timingGroupMap = timingMedicines.stream()
                .collect(Collectors.toMap(TimeZoneGroupDTO::getGroupLabel, Function.identity()));

        Map<String, TimeZoneGroupDTO> selectedTimeGroupMap = selectedTimeMedicines.stream()
                .collect(Collectors.toMap(TimeZoneGroupDTO::getGroupLabel, Function.identity()));

        // 各服薬方法の中の薬がすべてisCompletedか調べる
        Map<String, Boolean> hasContentMap = new HashMap<>();
        Map<String, Boolean> isCompletedMap = new HashMap<>();

        for (TimeZoneCategory category : TimeZoneCategory.values()) {
            String label = category.getLabel();
            String key = category.getAttrPrefix(); // 例: beforeBreakfast

            Map<String, TimeZoneGroupDTO> sourceMap =
                category.getCategory().equals("時間指定") ? selectedTimeGroupMap : timingGroupMap;

            boolean hasContent = sourceMap.containsKey(label);
            hasContentMap.put(key, hasContent);

            if (hasContent) {
                boolean isCompleted = userMedicineService.isAllMedicinesCompleted(userId, label);
                userMedicineService.checkLastMedicineTime(userId, label);
                isCompletedMap.put(key, isCompleted);
            }
        }

        // モデルにデータを追加し、ビュー(HTML)に渡す
        model.addAttribute("timingMedicines", timingMedicines);
        model.addAttribute("selectedTimeMedicines", selectedTimeMedicines);
        model.addAttribute("timeZoneCategories", Arrays.asList(TimeZoneCategory.values()));
        model.addAttribute("selectedTimeCategories", Arrays.stream(TimeZoneCategory.values()).filter(category -> "時間指定".equals(category.getCategory())).collect(Collectors.toList()));
        model.addAttribute("hasContentMap", hasContentMap);
        model.addAttribute("isCompletedMap", isCompletedMap);

        return "dashboard";  // dashboard.html を表示
    }
}
