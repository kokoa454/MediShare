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
