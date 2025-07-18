package com.medishare.service;

import java.util.List;

import com.medishare.dto.MedicineDTO;

public interface SearchService {
    public List<MedicineDTO> searchMedicine(String name);
}