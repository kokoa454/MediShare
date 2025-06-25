package com.medishare.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.medishare.dto.TimingGroupDTO;
import com.medishare.model.USER_MEDICINE;
import com.medishare.repository.UserMedicineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final UserMedicineRepository medicineRepository;

    public List<TimingGroupDTO> getGroupedMedicinesByUser(int userId) {
        List<USER_MEDICINE> allMedicines = medicineRepository.findByUserUserId(userId);

        // medicationMethod（例：「朝食前」）でグループ化
        Map<String, List<USER_MEDICINE>> grouped = allMedicines.stream()
                .collect(Collectors.groupingBy(USER_MEDICINE::getMedicationMethod));

        // DTOに変換
        List<TimingGroupDTO> result = grouped.entrySet().stream()
                .map(entry -> new TimingGroupDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(dto -> getTimingOrder(dto.getTimingLabel())))
                .collect(Collectors.toList());

        return result;
    }

    // 時間帯の表示順を固定（任意）
    private int getTimingOrder(String timingLabel) {
        List<String> order = Arrays.asList(
                "起床時", "朝食前", "朝食後", "昼食前", "昼食後", "夕食前", "夕食後", "就寝前", "食間"
        );
        return order.indexOf(timingLabel); // 見つからないと -1
    }
}
