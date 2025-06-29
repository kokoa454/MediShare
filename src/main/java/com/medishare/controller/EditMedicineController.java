package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medishare.service.MedicineService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/edit_medicine")
@RequiredArgsConstructor
public class EditMedicineController {
    private final MedicineService medicineService;

    @GetMapping
    public String edit_medicine(@RequestParam(name = "userMedicineId", required = true) int userMedicineId, Model model) {
        // ユーザーの薬の詳細を取得
        model.addAttribute("medicine", medicineService.getMedicineDetailsByUserMedicineId(userMedicineId));

        // タイミングの薬か時間指定の薬かを判定
        model.addAttribute("medicationMethod", medicineService.isTimingOrSelectedTimeMedicine(medicineService.getMedicineDetailsByUserMedicineId(userMedicineId)));
        
        // htmlのvalueに応じた値を返す
        model.addAttribute("medicationMethodValue", medicineService.getMedicationMethodValue(medicineService.isTimingOrSelectedTimeMedicine(medicineService.getMedicineDetailsByUserMedicineId(userMedicineId)), medicineService.getMedicineDetailsByUserMedicineId(userMedicineId)));

        return "edit_medicine";  // edit_medicine.html を表示
    }
}
