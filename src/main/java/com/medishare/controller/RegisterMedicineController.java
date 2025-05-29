package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register_medicine")
public class RegisterMedicineController {
    @GetMapping
    public String register_medicinePage() {
        return "register_medicine";
    }
}
