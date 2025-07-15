package com.medishare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medishare.model.USER_MEDICINE;
import com.medishare.service.UserMedicineService;
import com.medishare.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/register_medicine")
@RequiredArgsConstructor
public class RegisterMedicineController {
    private final UserMedicineService userMedicineService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(RegisterMedicineController.class);
    
    @GetMapping
    public String register_medicinePage(Model model) {
        model.addAttribute("userMedicine", new USER_MEDICINE()); 

        // register_medicine.htmlを表示
        return "register_medicine";
    }

    @PostMapping
    public String register_medicinePage(@ModelAttribute USER_MEDICINE userMedicine) {
        try{
            if (userMedicine.getMedicineOfficialName() != null && userMedicine.getMedicineOfficialName().isEmpty()) {
            userMedicine.setMedicineOfficialName(null);
            }
            if (userMedicine.getUrlKusurinoshiori() != null && userMedicine.getUrlKusurinoshiori().isEmpty()) {
                userMedicine.setUrlKusurinoshiori(null);
            }
            if (userMedicine.getUserComment() != null && userMedicine.getUserComment().isEmpty()) {
                userMedicine.setUserComment(null);
            }

            userMedicineService.registerMedicine(
                userMedicine.getMedicineUserInput(),
                userMedicine.getMedicineOfficialName(),
                userMedicine.getUrlKusurinoshiori(),
                userMedicine.getPrescriptionDays(),
                userMedicine.getUserComment(),
                userMedicine.getMedicationMethod()
            );

            logger.info("Successfully registered medicine: user ID={}, medicine={}", userService.getLoginUserId(), userMedicine);
        } catch (Exception e) {
            logger.error("Failed to register medicine: user ID={}, medicine={}, error message={}", userService.getLoginUserId(), userMedicine, e.getMessage());
        }
        
        return "redirect:/dashboard"; // dashboardへリダイレクト
    }
}
