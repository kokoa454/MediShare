package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medicine_dashboard")
public class MedicineDashboardController {
    @GetMapping
    public String medicineDashboardPage() {
        return "medicine_dashboard";
    }
}
