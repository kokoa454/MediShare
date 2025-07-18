package com.medishare.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "medicine", createIndex = false)
public class MedicineDocument {
    @Id
    private String medicineId;
    private String medicineOfficialName;
    private String urlKusurinoshiori;
}
