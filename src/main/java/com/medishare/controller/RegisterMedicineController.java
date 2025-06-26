package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.medishare.model.USER_MEDICINE;

@Controller
@RequestMapping("/register_medicine")
public class RegisterMedicineController {
    @GetMapping
    public String register_medicinePage(Model model) {
        model.addAttribute("userMedicine", new USER_MEDICINE());
        return "register_medicine";
    }
}
