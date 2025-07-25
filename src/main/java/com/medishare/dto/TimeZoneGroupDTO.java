package com.medishare.dto;

import java.util.List;

import com.medishare.model.UserMedicine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeZoneGroupDTO {
    private String groupLabel;
    private List<UserMedicine> medicines;
}
