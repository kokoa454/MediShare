package com.medishare.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.NoSuchIndexException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medishare.document.MedicineDocument;
import com.medishare.dto.MedicineDTO;
import com.medishare.model.Medicine;
import com.medishare.repository.MedicineDocumentRepository;
import com.medishare.repository.MedicineRepository;

import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class MedicineService {
    @Lazy
    private final MedicineDocumentRepository medicineDocumentRepository;
    private final MedicineRepository medicineRepository;
    private boolean elasticsearchAvailable = true;
    private static final Logger logger = LoggerFactory.getLogger(MedicineService.class);

    @PostConstruct
    public void init() {
        try {
            logger.info("Checking Elasticsearch availability...");

            if (medicineDocumentRepository.count() >= 0) {
                elasticsearchAvailable = true;
                logger.info("Elasticsearch is available.");

                syncToElasticsearch();
            }
        } catch (NoSuchIndexException e) {
            logger.warn("Elasticsearch index not found. Skipping sync.");
            elasticsearchAvailable = false;
        } catch (Exception e) {
            logger.error("Elasticsearch is not available. Skipping sync.", e);
            elasticsearchAvailable = false;
        }
    }

    public void syncToElasticsearch() {
        List<Medicine> medicines = medicineRepository.findAll();

        List<MedicineDocument> medicineDocuments = medicines.stream()
            .map(medicine -> new MedicineDocument(
                String.valueOf(medicine.getMedicineId()),
                medicine.getMedicineOfficialName(),
                medicine.getUrlKusurinoshiori()
            ))
            .collect(Collectors.toList());

        medicineDocumentRepository.deleteAll();
        medicineDocumentRepository.saveAll(medicineDocuments);
    }

    public List<MedicineDTO> searchMedicines(String searchOfficialMedicineName) {
        if (elasticsearchAvailable) {
            return medicineDocumentRepository.searchByMedicineOfficialName(searchOfficialMedicineName)
                .stream()
                .map(medicine -> new MedicineDTO(medicine.getMedicineOfficialName(), medicine.getUrlKusurinoshiori()))
                .collect(Collectors.toList());
        } else {
            return medicineRepository.findByMedicineOfficialNameContainingIgnoreCase(searchOfficialMedicineName)
                .stream()
                .map(medicine -> new MedicineDTO(medicine.getMedicineOfficialName(), medicine.getUrlKusurinoshiori()))
                .collect(Collectors.toList());
        }
    }
}
