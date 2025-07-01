package com.medishare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ReportDTO {
    private List<String> medicines; //実際に入っているのはUSER_MEDICINEのmedicineUserInput
    private String medicationMethod;
    private String userCondition;
    private String userComment;
}
