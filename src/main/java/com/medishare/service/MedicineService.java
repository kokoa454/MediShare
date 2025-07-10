package com.medishare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medishare.document.MedicineDocument;
import com.medishare.model.MEDICINE;
import com.medishare.repository.MedicineDocumentRepository;
import com.medishare.repository.MedicineRepository;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MedicineDocumentRepository medicineDocumentRepository;

    public void syncToElasticsearch() {
        List<MEDICINE> medicines = medicineRepository.findAll();

        List<MedicineDocument> medicineDocuments = medicines.stream()
            .map(medicine -> new MedicineDocument(
                String.valueOf(medicine.getMedicineId()),
                medicine.getMedicineOfficialName(),
                medicine.getUrlKusurinoShiori()
            ))
            .collect(Collectors.toList());

        medicineDocumentRepository.saveAll(medicineDocuments);

        System.out.println("Elasticsearch に " + medicineDocuments.size() + " 件の薬を登録しました。");
    }

    public List<MedicineDocument> searchMedicines(String searchOfficialMedicineName) {
        return medicineDocumentRepository.searchByMedicineOfficialName(searchOfficialMedicineName);
    }
}
