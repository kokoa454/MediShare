package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medishare.model.USER_MEDICINE;
import com.medishare.service.UserMedicineService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/edit_medicine")
@RequiredArgsConstructor
public class EditMedicineController {
    private final UserMedicineService userMedicineService;
    private static final Logger logger = LoggerFactory.getLogger(EditMedicineController.class);

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
        USER_MEDICINE userId = userMedicineService.getMedicineDetailsByUserMedicineId(userMedicine.getUserMedicineId());
        
        // 薬の情報を更新
        try{
            userMedicineService.updateMedicine(
            userMedicine.getUserMedicineId(),
            userMedicine.getMedicineUserInput(),
            userMedicine.getMedicineOfficialName(),
            userMedicine.getPrescriptionDays(),
            userMedicine.getUserComment(),
            userMedicine.getMedicationMethod()
        );

        logger.info("Successfully updated medicine information: user ID={}, medicine={}", userId, userMedicine);
        } catch (Exception e) {
            logger.error("Failed to update medicine information: user ID={}, medicine={}, error message={}", userId, userMedicine, e.getMessage());
        }
        // 例外が発生しても、リダイレクトする
        return "redirect:/dashboard";  // dashboardへリダイレクト
    }
}
