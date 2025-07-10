package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medishare.model.USER_MEDICINE;
import com.medishare.service.UserMedicineService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/edit_medicine")
@RequiredArgsConstructor
public class EditMedicineController {
    private final UserMedicineService userMedicineService;

    @GetMapping
    public String edit_medicine(@RequestParam(name = "userMedicineId", required = true) int userMedicineId, Model model) {
        // ユーザーの薬の詳細を取得
        model.addAttribute("medicine", userMedicineService.getMedicineDetailsByUserMedicineId(userMedicineId));

        // タイミングの薬か時間指定の薬かを判定
        model.addAttribute("medicationMethod", userMedicineService.isTimingOrSelectedTimeMedicine(userMedicineService.getMedicineDetailsByUserMedicineId(userMedicineId)));
        
        USER_MEDICINE medicine = userMedicineService.getMedicineDetailsByUserMedicineId(userMedicineId);
        model.addAttribute("userMedicine", medicine);

        return "edit_medicine";  // edit_medicine.html を表示
    }

    @PostMapping
    public String editMedicine(@ModelAttribute("userMedicine") USER_MEDICINE userMedicine) {        
        // 薬の情報を更新
        userMedicineService.updateMedicine(
            userMedicine.getUserMedicineId(),
            userMedicine.getMedicineUserInput(),
            userMedicine.getMedicineOfficialName(),
            userMedicine.getPrescriptionDays(),
            userMedicine.getUserComment(),
            userMedicine.getMedicationMethod()
        );

        return "redirect:/dashboard";  // dashboardへリダイレクト
    }
}
