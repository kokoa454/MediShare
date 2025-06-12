package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medicine_details")
public class medicineDetailsController {
    @GetMapping
    public String register_medicinePage() {
        return "medicine_details";
    }
}
