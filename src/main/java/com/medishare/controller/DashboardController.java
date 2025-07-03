package com.medishare.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medishare.dto.TimingGroupDTO;
import com.medishare.service.MedicineService;
import com.medishare.service.UserService;

import lombok.RequiredArgsConstructor;

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

        // 各グループの薬の有無を判定
        boolean hasWakeUpContent = timingMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("起床時"));
        boolean hasBeforeBreakfastContent = timingMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("朝食前"));
        boolean hasAfterBreakfastContent = timingMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("朝食後"));
        boolean hasBeforeLunchContent = timingMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("昼食前"));
        boolean hasAfterLunchContent = timingMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("昼食後"));
        boolean hasBeforeDinnerContent = timingMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("夕食前"));
        boolean hasAfterDinnerContent = timingMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("夕食後"));
        boolean hasBeforeSleepContent = timingMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("就寝前"));
        boolean hasBetweenMealContent = timingMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("食間"));

        boolean hasZeroOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("0時"));
        boolean hasOneOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("1時"));
        boolean hasTwoOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("2時"));
        boolean hasThreeOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("3時"));
        boolean hasFourOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("4時"));
        boolean hasFiveOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("5時"));
        boolean hasSixOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("6時"));
        boolean hasSevenOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("7時"));
        boolean hasEightOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("8時"));
        boolean hasNineOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("9時"));
        boolean hasTenOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("10時"));
        boolean hasElevenOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("11時"));
        boolean hasTwelveOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("12時"));
        boolean hasThirteenOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("13時"));
        boolean hasFourteenOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("14時"));
        boolean hasFifteenOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("15時"));
        boolean hasSixteenOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("16時"));
        boolean hasSeventeenOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("17時"));
        boolean hasEighteenOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("18時"));
        boolean hasNineteenOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("19時"));
        boolean hasTwentyOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("20時"));
        boolean hasTwentyOneOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("21時"));
        boolean hasTwentyTwoOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("22時"));
        boolean hasTwentyThreeOClockContent = selectedTimeMedicines.stream().anyMatch(timingGroup -> timingGroup.getGroupLabel().equals("23時"));

        // 各服薬方法の中の薬がすべてisCompletedか調べる
        if(hasWakeUpContent){
            boolean isWakeUpContentCompleted = medicineService.isAllMedicinesCompleted(userId, "起床時");
            medicineService.checkLastMedicineTime(userId, "起床時");
            model.addAttribute("isWakeUpCompleted", isWakeUpContentCompleted);
        } if(hasBeforeBreakfastContent){
            boolean isBeforeBreakfastContentCompleted = medicineService.isAllMedicinesCompleted(userId, "朝食前");
            medicineService.checkLastMedicineTime(userId, "朝食前");
            model.addAttribute("isBeforeBreakfastCompleted", isBeforeBreakfastContentCompleted);
        } if(hasAfterBreakfastContent){
            boolean isAfterBreakfastContentCompleted = medicineService.isAllMedicinesCompleted(userId, "朝食後");
            medicineService.checkLastMedicineTime(userId, "朝食後");
            model.addAttribute("isAfterBreakfastCompleted", isAfterBreakfastContentCompleted);
        } if(hasBeforeLunchContent){
            boolean isBeforeLunchContentCompleted = medicineService.isAllMedicinesCompleted(userId, "昼食前");
            medicineService.checkLastMedicineTime(userId, "昼食前");
            model.addAttribute("isBeforeLunchCompleted", isBeforeLunchContentCompleted);
        } if(hasAfterLunchContent){
            boolean isAfterLunchContentCompleted = medicineService.isAllMedicinesCompleted(userId, "昼食後");
            medicineService.checkLastMedicineTime(userId, "昼食後");
            model.addAttribute("isAfterLunchCompleted", isAfterLunchContentCompleted);
        } if(hasBeforeDinnerContent){
            boolean isBeforeDinnerContentCompleted = medicineService.isAllMedicinesCompleted(userId, "夕食前");
            medicineService.checkLastMedicineTime(userId, "夕食前");
            model.addAttribute("isBeforeDinnerCompleted", isBeforeDinnerContentCompleted);
        } if(hasAfterDinnerContent){
            boolean isAfterDinnerContentCompleted = medicineService.isAllMedicinesCompleted(userId, "夕食後");
            medicineService.checkLastMedicineTime(userId, "夕食後");
            model.addAttribute("isAfterDinnerCompleted", isAfterDinnerContentCompleted);
        } if(hasBeforeSleepContent){
            boolean isBeforeSleepContentCompleted = medicineService.isAllMedicinesCompleted(userId, "就寝前");
            medicineService.checkLastMedicineTime(userId, "就寝前");
            model.addAttribute("isBeforeSleepCompleted", isBeforeSleepContentCompleted);
        } if(hasBetweenMealContent){
            boolean isBetweenMealContentCompleted = medicineService.isAllMedicinesCompleted(userId, "食間");
            medicineService.checkLastMedicineTime(userId, "食間");
            model.addAttribute("isBetweenMealCompleted", isBetweenMealContentCompleted);
        }

        if(hasZeroOClockContent){
            boolean isZeroOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "0時");
            medicineService.checkLastMedicineTime(userId, "0時");
            model.addAttribute("isZeroOClockCompleted", isZeroOClockContentCompleted);
        } if(hasOneOClockContent){
            boolean isOneOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "1時");
            medicineService.checkLastMedicineTime(userId, "1時");
            model.addAttribute("isOneOClockCompleted", isOneOClockContentCompleted);
        } if(hasTwoOClockContent){
            boolean isTwoOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "2時");
            medicineService.checkLastMedicineTime(userId, "2時");
            model.addAttribute("isTwoOClockCompleted", isTwoOClockContentCompleted);
        } if(hasThreeOClockContent){
            boolean isThreeOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "3時");
            medicineService.checkLastMedicineTime(userId, "3時");
            model.addAttribute("isThreeOClockCompleted", isThreeOClockContentCompleted);
        } if(hasFourOClockContent){
            boolean isFourOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "4時");
            medicineService.checkLastMedicineTime(userId, "4時");
            model.addAttribute("isFourOClockCompleted", isFourOClockContentCompleted);
        } if(hasFiveOClockContent){
            boolean isFiveOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "5時");
            medicineService.checkLastMedicineTime(userId, "5時");
            model.addAttribute("isFiveOClockCompleted", isFiveOClockContentCompleted);
        } if(hasSixOClockContent){
            boolean isSixOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "6時");
            medicineService.checkLastMedicineTime(userId, "6時");
            model.addAttribute("isSixOClockCompleted", isSixOClockContentCompleted);
        } if(hasSevenOClockContent){
            boolean isSevenOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "7時");
            medicineService.checkLastMedicineTime(userId, "7時");
            model.addAttribute("isSevenOClockCompleted", isSevenOClockContentCompleted);
        } if(hasEightOClockContent){
            boolean isEightOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "8時");
            medicineService.checkLastMedicineTime(userId, "8時");
            model.addAttribute("isEightOClockCompleted", isEightOClockContentCompleted);
        } if(hasNineOClockContent){
            boolean isNineOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "9時");
            medicineService.checkLastMedicineTime(userId, "9時");
            model.addAttribute("isNineOClockCompleted", isNineOClockContentCompleted);
        } if(hasTenOClockContent){
            boolean isTenOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "10時");
            medicineService.checkLastMedicineTime(userId, "10時");
            model.addAttribute("isTenOClockCompleted", isTenOClockContentCompleted);
        } if(hasElevenOClockContent){
            boolean isElevenOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "11時");
            medicineService.checkLastMedicineTime(userId, "11時");
            model.addAttribute("isElevenOClockCompleted", isElevenOClockContentCompleted);
        } if(hasTwelveOClockContent){
            boolean isTwelveOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "12時");
            medicineService.checkLastMedicineTime(userId, "12時");
            model.addAttribute("isTwelveOClockCompleted", isTwelveOClockContentCompleted);
            model.addAttribute("isTwelveOClockCompleted", isTwelveOClockContentCompleted);
        } if(hasThirteenOClockContent){
            boolean isThirteenOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "13時");
            medicineService.checkLastMedicineTime(userId, "13時");
            model.addAttribute("isThirteenOClockCompleted", isThirteenOClockContentCompleted);
        } if(hasFourteenOClockContent){
            boolean isFourteenOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "14時");
            medicineService.checkLastMedicineTime(userId, "14時");
            model.addAttribute("isFourteenOClockCompleted", isFourteenOClockContentCompleted);
        } if(hasFifteenOClockContent){
            boolean isFifteenOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "15時");
            medicineService.checkLastMedicineTime(userId, "15時");
            model.addAttribute("isFifteenOClockCompleted", isFifteenOClockContentCompleted);
        } if(hasSixteenOClockContent){
            boolean isSixteenOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "16時");
            medicineService.checkLastMedicineTime(userId, "16時");
            model.addAttribute("isSixteenOClockCompleted", isSixteenOClockContentCompleted);
        } if(hasSeventeenOClockContent){
            boolean isSeventeenOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "17時");
            medicineService.checkLastMedicineTime(userId, "17時");
            model.addAttribute("isSeventeenOClockCompleted", isSeventeenOClockContentCompleted);
        } if(hasEighteenOClockContent){
            boolean isEighteenOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "18時");
            medicineService.checkLastMedicineTime(userId, "18時");
            model.addAttribute("isEighteenOClockCompleted", isEighteenOClockContentCompleted);
        } if(hasNineteenOClockContent){
            boolean isNineteenOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "19時");
            medicineService.checkLastMedicineTime(userId, "19時");
            model.addAttribute("isNineteenOClockCompleted", isNineteenOClockContentCompleted);
        } if(hasTwentyOClockContent){
            boolean isTwentyOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "20時");
            medicineService.checkLastMedicineTime(userId, "20時");
            model.addAttribute("isTwentyOClockCompleted", isTwentyOClockContentCompleted);
        } if(hasTwentyOneOClockContent){
            boolean isTwentyOneOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "21時");
            medicineService.checkLastMedicineTime(userId, "21時");
            model.addAttribute("isTwentyOneOClockCompleted", isTwentyOneOClockContentCompleted);
        } if(hasTwentyTwoOClockContent){
            boolean isTwentyTwoOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "22時");
            medicineService.checkLastMedicineTime(userId, "22時");
            model.addAttribute("isTwentyTwoOClockCompleted", isTwentyTwoOClockContentCompleted);
        } if(hasTwentyThreeOClockContent){
            boolean isTwentyThreeOClockContentCompleted = medicineService.isAllMedicinesCompleted(userId, "23時");
            medicineService.checkLastMedicineTime(userId, "23時");
            model.addAttribute("isTwentyThreeOClockCompleted", isTwentyThreeOClockContentCompleted);
        }

        // モデルにデータを追加し、ビュー(HTML)に渡す
        model.addAttribute("timingMedicines", timingMedicines);
        model.addAttribute("hasWakeUpContent", hasWakeUpContent);
        model.addAttribute("hasBeforeBreakfastContent", hasBeforeBreakfastContent);
        model.addAttribute("hasAfterBreakfastContent", hasAfterBreakfastContent);
        model.addAttribute("hasBeforeLunchContent", hasBeforeLunchContent);
        model.addAttribute("hasAfterLunchContent", hasAfterLunchContent);
        model.addAttribute("hasBeforeDinnerContent", hasBeforeDinnerContent);
        model.addAttribute("hasAfterDinnerContent", hasAfterDinnerContent);
        model.addAttribute("hasBeforeSleepContent", hasBeforeSleepContent);
        model.addAttribute("hasBetweenMealContent", hasBetweenMealContent);

        model.addAttribute("selectedTimeMedicines", selectedTimeMedicines);
        model.addAttribute("hasZeroOClockContent", hasZeroOClockContent);
        model.addAttribute("hasOneOClockContent", hasOneOClockContent);
        model.addAttribute("hasTwoOClockContent", hasTwoOClockContent);
        model.addAttribute("hasThreeOClockContent", hasThreeOClockContent);
        model.addAttribute("hasFourOClockContent", hasFourOClockContent);
        model.addAttribute("hasFiveOClockContent", hasFiveOClockContent);
        model.addAttribute("hasSixOClockContent", hasSixOClockContent);
        model.addAttribute("hasSevenOClockContent", hasSevenOClockContent);
        model.addAttribute("hasEightOClockContent", hasEightOClockContent);
        model.addAttribute("hasNineOClockContent", hasNineOClockContent);
        model.addAttribute("hasTenOClockContent", hasTenOClockContent);
        model.addAttribute("hasElevenOClockContent", hasElevenOClockContent);
        model.addAttribute("hasTwelveOClockContent", hasTwelveOClockContent);
        model.addAttribute("hasThirteenOClockContent", hasThirteenOClockContent);
        model.addAttribute("hasFourteenOClockContent", hasFourteenOClockContent);
        model.addAttribute("hasFifteenOClockContent", hasFifteenOClockContent);
        model.addAttribute("hasSixteenOClockContent", hasSixteenOClockContent);
        model.addAttribute("hasSeventeenOClockContent", hasSeventeenOClockContent);
        model.addAttribute("hasEighteenOClockContent", hasEighteenOClockContent);
        model.addAttribute("hasNineteenOClockContent", hasNineteenOClockContent);
        model.addAttribute("hasTwentyOClockContent", hasTwentyOClockContent);
        model.addAttribute("hasTwentyOneOClockContent", hasTwentyOneOClockContent);
        model.addAttribute("hasTwentyTwoOClockContent", hasTwentyTwoOClockContent);
        model.addAttribute("hasTwentyThreeOClockContent", hasTwentyThreeOClockContent);


        return "dashboard";  // dashboard.html を表示
    }
}
