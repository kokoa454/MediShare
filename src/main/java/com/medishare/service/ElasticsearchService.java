package com.medishare.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.medishare.dto.MedicineDTO;
import com.medishare.repository.MedicineDocumentRepository;
import com.medishare.document.MedicineDocument;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "search.engine", havingValue = "elasticsearch")

public class ElasticsearchService implements SearchService {

    private final MedicineDocumentRepository medicineDocumentRepository;

    @Override
    public List<MedicineDTO> searchMedicine(String name) {
        return medicineDocumentRepository.searchByMedicineOfficialName(name)
                .stream()
                .map(medicine -> new MedicineDTO(medicine.getMedicineOfficialName(), medicine.getUrlKusurinoshiori()))
                .collect(Collectors.toList());
    }

    public void saveAll(List<MedicineDocument> medicineDocuments) {
        medicineDocumentRepository.saveAll(medicineDocuments);
    }
}
