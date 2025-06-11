package com.medishare.dto;

import com.medishare.model.USER_MEDICINE;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TimingGroupDTO {
    private String timingLabel;
    private List<USER_MEDICINE> medicines;
}
