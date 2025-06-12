package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report_medicine")
public class ReportMedicineController {
    @GetMapping
    public String report_medicinePage() {
        return "report_medicine";
    }
}
