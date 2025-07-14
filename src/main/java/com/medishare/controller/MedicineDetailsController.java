package com.medishare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medishare.service.UserMedicineService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/medicine_details")
@RequiredArgsConstructor
public class MedicineDetailsController {
    private final UserMedicineService userMedicineService;

    @GetMapping
    public String register_medicinePage(@RequestParam(name = "userMedicineId", required = true) int userMedicineId, Model model) {
        model.addAttribute("medicine", userMedicineService.getMedicineDetailsByUserMedicineId(userMedicineId));

        return "medicine_details";
    }
}
