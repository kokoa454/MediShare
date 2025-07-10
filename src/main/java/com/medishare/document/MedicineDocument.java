package com.medishare.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(indexName = "medicines")
public class MedicineDocument {
    @Id
    private String medicineId;
    private String medicineOfficialName;
    private String urlKusurinoShiori;
}
