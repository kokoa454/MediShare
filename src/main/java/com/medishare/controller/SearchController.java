package com.medishare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medishare.dto.MedicineDTO;
import com.medishare.service.MedicineService;

@RestController
public class SearchController {
    @Autowired
    private MedicineService medicineService;

    @GetMapping("/searchMedicineOfficialName")
    public List<MedicineDTO> searchOfficialMedicineName(@RequestParam String name) {
        return medicineService.searchMedicines(name);
    }
}
