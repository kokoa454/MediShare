package com.medishare.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medishare.service.MedicineService;

import jakarta.annotation.PostConstruct;

@Component
public class StartupConfig {
    @Autowired
    private MedicineService medicineService;

    @PostConstruct
    public void init() {
        medicineService.syncToElasticsearch();
    }
}
