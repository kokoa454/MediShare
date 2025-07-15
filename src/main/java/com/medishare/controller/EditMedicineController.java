package com.medishare.controller;

import com.medishare.document.MedicineDocument;
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
import com.medishare.service.MedicineService;
import com.medishare.service.UserMedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/edit_medicine")
@RequiredArgsConstructor
public class EditMedicineController {

    private final UserMedicineService userMedicineService;
    private final MedicineService medicineService; // ← Elasticsearch用サービスを追加
    private static final Logger logger = LoggerFactory.getLogger(EditMedicineController.class);

    @GetMapping
    public String edit_medicine(@RequestParam(name = "userMedicineId") int userMedicineId, Model model) {
        model.addAttribute("medicine", userMedicineService.getMedicineDetailsByUserMedicineId(userMedicineId));
        model.addAttribute("medicationMethod",
                userMedicineService.isTimingOrSelectedTimeMedicine(
                        userMedicineService.getMedicineDetailsByUserMedicineId(userMedicineId)
                ));
        model.addAttribute("userMedicine", userMedicineService.getMedicineDetailsByUserMedicineId(userMedicineId));

        return "edit_medicine";
    }

    @PostMapping
    public String editMedicine(@ModelAttribute("userMedicine") USER_MEDICINE userMedicine) {
        userMedicineService.updateMedicine(
                userMedicine.getUserMedicineId(),
                userMedicine.getMedicineUserInput(),
                userMedicine.getMedicineOfficialName(),
                userMedicine.getPrescriptionDays(),
                userMedicine.getUserComment(),
                userMedicine.getMedicationMethod()
        );
        return "redirect:/dashboard";
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

