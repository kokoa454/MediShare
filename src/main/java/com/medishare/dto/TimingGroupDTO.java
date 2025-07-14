package com.medishare.dto;

import java.util.List;

import com.medishare.model.USER_MEDICINE;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimingGroupDTO {
    private String groupLabel;
    private List<USER_MEDICINE> medicines;
}
