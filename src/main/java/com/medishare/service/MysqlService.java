package com.medishare.service;

import com.medishare.dto.MedicineDTO;
import com.medishare.repository.MedicineRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MysqlService implements SearchService{
    private final MedicineRepository medicineRepository;

    @Override
    public List<MedicineDTO> searchMedicine(String name) {
        return medicineRepository.findByMedicineOfficialNameContainingIgnoreCase(name)
                .stream()
                .map(medicine -> new MedicineDTO(medicine.getMedicineOfficialName(), medicine.getUrlKusurinoshiori()))
                .collect(Collectors.toList());
    }
}
