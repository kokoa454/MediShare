package com.medishare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineDTO {
    private String medicineOfficialName;
    private String urlKusurinoshiori;
}
