package com.medishare.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medishare.dto.ReportDTO;
import com.medishare.service.MailService;
import com.medishare.service.MedicineService;
import com.medishare.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/report_medicine")
@RequiredArgsConstructor
public class ReportMedicineController {
    private final MedicineService medicineService;
    private final UserService userService;
    private final MailService mailService;

    @GetMapping
    public String report_medicinePage(@RequestParam(name = "method", required = true) String method, @RequestParam(name = "userMedicineIds", required = true) String userMedicineIds, Model model) {
        String medicationMethod = "";

        switch(method){
            case "wakeUp" -> medicationMethod = "起床時";
            case "beforeBreakfast" -> medicationMethod = "朝食前";
            case "afterBreakfast" -> medicationMethod = "朝食後";
            case "beforeLunch" -> medicationMethod = "昼食前";
            case "afterLunch" -> medicationMethod = "昼食後";
            case "beforeDinner" -> medicationMethod = "夕食前";
            case "afterDinner" -> medicationMethod = "夕食後";
            case "beforeSleep" -> medicationMethod = "就寝前";
            case "betweenMeal" -> medicationMethod = "食間";
            case "zeroOClock" -> medicationMethod = "0時";
            case "oneOClock" -> medicationMethod = "1時";
            case "twoOClock" -> medicationMethod = "2時";
            case "threeOClock" -> medicationMethod = "3時";
            case "fourOClock" -> medicationMethod = "4時";
            case "fiveOClock" -> medicationMethod = "5時";
            case "sixOClock" -> medicationMethod = "6時";
            case "sevenOClock" -> medicationMethod = "7時";
            case "eightOClock" -> medicationMethod = "8時";
            case "nineOClock" -> medicationMethod = "9時";
            case "tenOClock" -> medicationMethod = "10時";
            case "elevenOClock" -> medicationMethod = "11時";
            case "twelveOClock" -> medicationMethod = "12時";
            case "thirteenOClock" -> medicationMethod = "13時";
            case "fourteenOClock" -> medicationMethod = "14時";
            case "fifteenOClock" -> medicationMethod = "15時";
            case "sixteenOClock" -> medicationMethod = "16時";
            case "seventeenOClock" -> medicationMethod = "17時";
            case "eighteenOClock" -> medicationMethod = "18時";
            case "nineteenOClock" -> medicationMethod = "19時";
            case "twentyOClock" -> medicationMethod = "20時";
            case "twentyOneOClock" -> medicationMethod = "21時";
            case "twentyTwoOClock" -> medicationMethod = "22時";
            case "twentyThreeOClock" -> medicationMethod = "23時";
        }

        model.addAttribute("medicationMethod", medicationMethod);

        List<String> medicines = new ArrayList<>();
        for(String userMedicineId : userMedicineIds.split(",")) {
            medicines.add(medicineService.getMedicineUserInputByUserMedicineId(Integer.parseInt(userMedicineId)).split(",")[0]);
        }

        model.addAttribute("medicines", medicines);

        int userId = userService.getLoginUserId();
        String familyEmail = userService.getFamilyEmail(userId);
        
        model.addAttribute("familyEmail", familyEmail);

        return "report_medicine";
    }

    @PostMapping
    public void report_medicinePage(@RequestBody ReportDTO reportData, @RequestParam(name = "userMedicineIds", required = true) String userMedicineIds) {
        String userEmail = reportData.getUserEmail();
        String familyEmail = reportData.getFamilyEmail();
        List<String> medicineNames = reportData.getMedicines();
        String medicationMethod = reportData.getMedicationMethod();
        String userCondition = reportData.getUserCondition();
        String userComment = reportData.getUserComment();

        System.out.println(reportData);

        mailService.sendMail(userEmail ,familyEmail, medicineNames, medicationMethod, userCondition, userComment);

        for(String userMedicineId : userMedicineIds.split(",")) {
            medicineService.completeMedicine(Integer.parseInt(userMedicineId));
        }
    }
}
