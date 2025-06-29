package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medishare.model.USER_MEDICINE;
import com.medishare.service.MedicineService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/register_medicine")
@RequiredArgsConstructor
public class RegisterMedicineController {
    private final MedicineService medicineService;
    
    @GetMapping
    public String register_medicinePage(Model model) {
        model.addAttribute("userMedicine", new USER_MEDICINE()); 

        // register_medicine.htmlを表示
        return "register_medicine";
    }

    @PostMapping
    public String register_medicinePage(@ModelAttribute USER_MEDICINE userMedicine) {
        medicineService.registerMedicine(
            userMedicine.getMedicineUserInput(),
            userMedicine.getMedicineOfficialName(),
            userMedicine.getPrescriptionDays(),
            userMedicine.getUserComment(),
            userMedicine.getMedicationMethod()
        );
        
        return "redirect:/dashboard"; // dashboardへリダイレクト
    }
}
