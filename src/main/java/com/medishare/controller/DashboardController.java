package com.medishare.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medishare.dto.TimingGroupDTO;
import com.medishare.service.UserMedicineService;
import com.medishare.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {
    private final UserMedicineService userMedicineService;
    private final UserService userService;

    @GetMapping
    public String dashboardPage(Model model) {
        int userId = userService.getLoginUserId(); // ログインユーザーのIDを取得

        List<TimingGroupDTO> timingMedicines = userMedicineService.getTimingMedicinesByUser(userId); // ユーザーの時間帯の薬を取得してグループ化
        List<TimingGroupDTO> selectedTimeMedicines = userMedicineService.getSelectedTimeMedicinesByUser(userId); // ユーザーの時間指定の薬を取得してグループ化

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
            boolean isWakeUpContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "起床時");
            userMedicineService.checkLastMedicineTime(userId, "起床時");
            model.addAttribute("isWakeUpCompleted", isWakeUpContentCompleted);
        } if(hasBeforeBreakfastContent){
            boolean isBeforeBreakfastContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "朝食前");
            userMedicineService.checkLastMedicineTime(userId, "朝食前");
            model.addAttribute("isBeforeBreakfastCompleted", isBeforeBreakfastContentCompleted);
        } if(hasAfterBreakfastContent){
            boolean isAfterBreakfastContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "朝食後");
            userMedicineService.checkLastMedicineTime(userId, "朝食後");
            model.addAttribute("isAfterBreakfastCompleted", isAfterBreakfastContentCompleted);
        } if(hasBeforeLunchContent){
            boolean isBeforeLunchContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "昼食前");
            userMedicineService.checkLastMedicineTime(userId, "昼食前");
            model.addAttribute("isBeforeLunchCompleted", isBeforeLunchContentCompleted);
        } if(hasAfterLunchContent){
            boolean isAfterLunchContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "昼食後");
            userMedicineService.checkLastMedicineTime(userId, "昼食後");
            model.addAttribute("isAfterLunchCompleted", isAfterLunchContentCompleted);
        } if(hasBeforeDinnerContent){
            boolean isBeforeDinnerContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "夕食前");
            userMedicineService.checkLastMedicineTime(userId, "夕食前");
            model.addAttribute("isBeforeDinnerCompleted", isBeforeDinnerContentCompleted);
        } if(hasAfterDinnerContent){
            boolean isAfterDinnerContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "夕食後");
            userMedicineService.checkLastMedicineTime(userId, "夕食後");
            model.addAttribute("isAfterDinnerCompleted", isAfterDinnerContentCompleted);
        } if(hasBeforeSleepContent){
            boolean isBeforeSleepContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "就寝前");
            userMedicineService.checkLastMedicineTime(userId, "就寝前");
            model.addAttribute("isBeforeSleepCompleted", isBeforeSleepContentCompleted);
        } if(hasBetweenMealContent){
            boolean isBetweenMealContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "食間");
            userMedicineService.checkLastMedicineTime(userId, "食間");
            model.addAttribute("isBetweenMealCompleted", isBetweenMealContentCompleted);
        }

        if(hasZeroOClockContent){
            boolean isZeroOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "0時");
            userMedicineService.checkLastMedicineTime(userId, "0時");
            model.addAttribute("isZeroOClockCompleted", isZeroOClockContentCompleted);
        } if(hasOneOClockContent){
            boolean isOneOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "1時");
            userMedicineService.checkLastMedicineTime(userId, "1時");
            model.addAttribute("isOneOClockCompleted", isOneOClockContentCompleted);
        } if(hasTwoOClockContent){
            boolean isTwoOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "2時");
            userMedicineService.checkLastMedicineTime(userId, "2時");
            model.addAttribute("isTwoOClockCompleted", isTwoOClockContentCompleted);
        } if(hasThreeOClockContent){
            boolean isThreeOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "3時");
            userMedicineService.checkLastMedicineTime(userId, "3時");
            model.addAttribute("isThreeOClockCompleted", isThreeOClockContentCompleted);
        } if(hasFourOClockContent){
            boolean isFourOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "4時");
            userMedicineService.checkLastMedicineTime(userId, "4時");
            model.addAttribute("isFourOClockCompleted", isFourOClockContentCompleted);
        } if(hasFiveOClockContent){
            boolean isFiveOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "5時");
            userMedicineService.checkLastMedicineTime(userId, "5時");
            model.addAttribute("isFiveOClockCompleted", isFiveOClockContentCompleted);
        } if(hasSixOClockContent){
            boolean isSixOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "6時");
            userMedicineService.checkLastMedicineTime(userId, "6時");
            model.addAttribute("isSixOClockCompleted", isSixOClockContentCompleted);
        } if(hasSevenOClockContent){
            boolean isSevenOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "7時");
            userMedicineService.checkLastMedicineTime(userId, "7時");
            model.addAttribute("isSevenOClockCompleted", isSevenOClockContentCompleted);
        } if(hasEightOClockContent){
            boolean isEightOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "8時");
            userMedicineService.checkLastMedicineTime(userId, "8時");
            model.addAttribute("isEightOClockCompleted", isEightOClockContentCompleted);
        } if(hasNineOClockContent){
            boolean isNineOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "9時");
            userMedicineService.checkLastMedicineTime(userId, "9時");
            model.addAttribute("isNineOClockCompleted", isNineOClockContentCompleted);
        } if(hasTenOClockContent){
            boolean isTenOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "10時");
            userMedicineService.checkLastMedicineTime(userId, "10時");
            model.addAttribute("isTenOClockCompleted", isTenOClockContentCompleted);
        } if(hasElevenOClockContent){
            boolean isElevenOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "11時");
            userMedicineService.checkLastMedicineTime(userId, "11時");
            model.addAttribute("isElevenOClockCompleted", isElevenOClockContentCompleted);
        } if(hasTwelveOClockContent){
            boolean isTwelveOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "12時");
            userMedicineService.checkLastMedicineTime(userId, "12時");
            model.addAttribute("isTwelveOClockCompleted", isTwelveOClockContentCompleted);
            model.addAttribute("isTwelveOClockCompleted", isTwelveOClockContentCompleted);
        } if(hasThirteenOClockContent){
            boolean isThirteenOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "13時");
            userMedicineService.checkLastMedicineTime(userId, "13時");
            model.addAttribute("isThirteenOClockCompleted", isThirteenOClockContentCompleted);
        } if(hasFourteenOClockContent){
            boolean isFourteenOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "14時");
            userMedicineService.checkLastMedicineTime(userId, "14時");
            model.addAttribute("isFourteenOClockCompleted", isFourteenOClockContentCompleted);
        } if(hasFifteenOClockContent){
            boolean isFifteenOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "15時");
            userMedicineService.checkLastMedicineTime(userId, "15時");
            model.addAttribute("isFifteenOClockCompleted", isFifteenOClockContentCompleted);
        } if(hasSixteenOClockContent){
            boolean isSixteenOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "16時");
            userMedicineService.checkLastMedicineTime(userId, "16時");
            model.addAttribute("isSixteenOClockCompleted", isSixteenOClockContentCompleted);
        } if(hasSeventeenOClockContent){
            boolean isSeventeenOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "17時");
            userMedicineService.checkLastMedicineTime(userId, "17時");
            model.addAttribute("isSeventeenOClockCompleted", isSeventeenOClockContentCompleted);
        } if(hasEighteenOClockContent){
            boolean isEighteenOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "18時");
            userMedicineService.checkLastMedicineTime(userId, "18時");
            model.addAttribute("isEighteenOClockCompleted", isEighteenOClockContentCompleted);
        } if(hasNineteenOClockContent){
            boolean isNineteenOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "19時");
            userMedicineService.checkLastMedicineTime(userId, "19時");
            model.addAttribute("isNineteenOClockCompleted", isNineteenOClockContentCompleted);
        } if(hasTwentyOClockContent){
            boolean isTwentyOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "20時");
            userMedicineService.checkLastMedicineTime(userId, "20時");
            model.addAttribute("isTwentyOClockCompleted", isTwentyOClockContentCompleted);
        } if(hasTwentyOneOClockContent){
            boolean isTwentyOneOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "21時");
            userMedicineService.checkLastMedicineTime(userId, "21時");
            model.addAttribute("isTwentyOneOClockCompleted", isTwentyOneOClockContentCompleted);
        } if(hasTwentyTwoOClockContent){
            boolean isTwentyTwoOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "22時");
            userMedicineService.checkLastMedicineTime(userId, "22時");
            model.addAttribute("isTwentyTwoOClockCompleted", isTwentyTwoOClockContentCompleted);
        } if(hasTwentyThreeOClockContent){
            boolean isTwentyThreeOClockContentCompleted = userMedicineService.isAllMedicinesCompleted(userId, "23時");
            userMedicineService.checkLastMedicineTime(userId, "23時");
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
