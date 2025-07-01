package com.medishare.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private String userEmail;
    private String familyEmail;
    private List<String> medicines; //実際に入っているのはUSER_MEDICINEのmedicineUserInput
    private String medicationMethod;
    private String userCondition;
    private String userComment;
}
