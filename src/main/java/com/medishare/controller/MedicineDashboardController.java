package com.medishare.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medishare.model.USER_MEDICINE;
import com.medishare.repository.UserMedicineRepository;
import com.medishare.service.MedicineService;
import com.medishare.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/medicine_dashboard")
@RequiredArgsConstructor
public class MedicineDashboardController {
    private final MedicineService medicineService;
    private final UserService userService;
    private final UserMedicineRepository userMedicineRepository;

    @GetMapping
    public String medicineDashboardPage(@RequestParam(name = "method", required = true) String method, @RequestParam(name = "search", required = false) String searchKeyword ,Model model) {
        // ログインユーザーのIDを取得
        int userId = userService.getLoginUserId();
        String titleName = "";

        switch(method){
            case "wakeUp" -> titleName = "起床時";
            case "beforeBreakfast" -> titleName = "朝食前";
            case "afterBreakfast" -> titleName = "朝食後";
            case "beforeLunch" -> titleName = "昼食前";
            case "afterLunch" -> titleName = "昼食後";
            case "beforeDinner" -> titleName = "夕食前";
            case "afterDinner" -> titleName = "夕食後";
            case "beforeSleep" -> titleName = "就寝前";
            case "betweenMeal" -> titleName = "食間";
            case "zeroOClock" -> titleName = "0時";
            case "oneOClock" -> titleName = "1時";
            case "twoOClock" -> titleName = "2時";
            case "threeOClock" -> titleName = "3時";
            case "fourOClock" -> titleName = "4時";
            case "fiveOClock" -> titleName = "5時";
            case "sixOClock" -> titleName = "6時";
            case "sevenOClock" -> titleName = "7時";
            case "eightOClock" -> titleName = "8時";
            case "nineOClock" -> titleName = "9時";
            case "tenOClock" -> titleName = "10時";
            case "elevenOClock" -> titleName = "11時";
            case "twelveOClock" -> titleName = "12時";
            case "thirteenOClock" -> titleName = "13時";
            case "fourteenOClock" -> titleName = "14時";
            case "fifteenOClock" -> titleName = "15時";
            case "sixteenOClock" -> titleName = "16時";
            case "seventeenOClock" -> titleName = "17時";
            case "eighteenOClock" -> titleName = "18時";
            case "nineteenOClock" -> titleName = "19時";
            case "twentyOClock" -> titleName = "20時";
            case "twentyOneOClock" -> titleName = "21時";
            case "twentyTwoOClock" -> titleName = "22時";
            case "twentyThreeOClock" -> titleName = "23時";
        }

        model.addAttribute("titleName", titleName);

        model.addAttribute("method", method);

        List<USER_MEDICINE> medicines;

        if(searchKeyword != null) {
            medicines = medicineService.getMedicineListByUserAndMedicationMethodAndSearch(userId, titleName, searchKeyword);
        } else {
            medicines = medicineService.getMedicineListByUserAndMedicationMethod(userId, titleName);
        }
        model.addAttribute("medicines", medicines);        

        return "medicine_dashboard";
    }

    @PostMapping("/delete_medicine")
    public String deleteMedicine(@RequestBody List<Integer> userMedicineIds) {
        medicineService.deleteMedicine(userMedicineIds);
        return "redirect:/dashboard";
    }
}
