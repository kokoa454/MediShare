package com.medishare.service;

import java.util.ArrayList;
import java.util.Arrays;
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

    // // 時間帯の表示順を固定（任意）
    // private int getTimingOrder(String timingLabel) {
    //     List<String> order = Arrays.asList(
    //             "起床時", "朝食前", "朝食後", "昼食前", "昼食後", "夕食前", "夕食後", "就寝前", "食間"
    //     );
    //     return order.indexOf(timingLabel); // 見つからないと -1
    // }

    // 時間帯の薬のみをフィルタリングし、グループ化
    public List<TimingGroupDTO> getTimingMedicinesByUser(int userId){
        List<USER_MEDICINE> allMedicines = medicineRepository.findByUserUserIdOrderByUserMedicineIdAsc(userId);
        Map<String, List<USER_MEDICINE>> groupedMedicines = allMedicines.stream()
            .filter(medicine -> isTimingMethod(medicine.getMedicationMethod())) // 時間帯の薬のみフィルタリング
            .collect(Collectors.groupingBy(USER_MEDICINE::getMedicationMethod));

        List<TimingGroupDTO> timingMedicines = new ArrayList<>();
        for (Map.Entry<String, List<USER_MEDICINE>> entry : groupedMedicines.entrySet()) {
            timingMedicines.add(new TimingGroupDTO(entry.getKey(), entry.getValue()));
        }

        return timingMedicines;
    }

    // ユーザーの薬を取得して、時間帯ごとにグループ化
    private boolean isTimingMethod(String method) {
        return Arrays.asList("起床時", "朝食前", "朝食後", "昼食前", "昼食後", "夕食前", "夕食後", "就寝前", "食間").contains(method);
    }

    // 時間指定の薬のみをフィルタリングし、グループ化
    public List<TimingGroupDTO> getSelectedTimeMedicinesByUser(int userId){
        List<USER_MEDICINE> allMedicines = medicineRepository.findByUserUserIdOrderByUserMedicineIdAsc(userId);
        Map<String, List<USER_MEDICINE>> groupedMedicines = allMedicines.stream()
            .filter(medicine -> isSelectedTimeMethod(medicine.getMedicationMethod())) // 時間帯の薬のみフィルタリング
            .collect(Collectors.groupingBy(USER_MEDICINE::getMedicationMethod));

        List<TimingGroupDTO> selectedTimeMedicines = new ArrayList<>();
        for (Map.Entry<String, List<USER_MEDICINE>> entry : groupedMedicines.entrySet()) {
            selectedTimeMedicines.add(new TimingGroupDTO(entry.getKey(), entry.getValue()));
        }

        return selectedTimeMedicines;
    }

    // ユーザーの薬を取得して、時間指定ごとにグループ化
    private boolean isSelectedTimeMethod(String method) {
        return Arrays.asList("0時", "1時", "2時", "3時", "4時", "5時", "6時", "7時", "8時", "9時", "10時", "11時", "12時", "13時", "14時", "15時", "16時", "17時", "18時", "19時", "20時", "21時", "22時", "23時").contains(method);
    }

    public List<USER_MEDICINE> getMedicineListByUserAndMedicationMethod(int userId, String medicationMethod) {
        // ユーザーIDと投薬方法でフィルタリング
        return  medicineRepository.findByUserUserIdAndMedicationMethod(userId, medicationMethod);
    }
}