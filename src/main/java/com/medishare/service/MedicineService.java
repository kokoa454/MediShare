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

    // ユーザーIDと投薬方法でフィルタリング
    public List<USER_MEDICINE> getMedicineListByUserAndMedicationMethod(int userId, String medicationMethod) {
        return  medicineRepository.findByUserUserIdAndMedicationMethod(userId, medicationMethod);
    }

    // USER_MEDICINEのuserMedicineIdで薬を取得
    public USER_MEDICINE getMedicineDetailsByUserMedicineId(int userMedicineId) {
        return medicineRepository.findByUserMedicineId(userMedicineId);
    }

    // medicine.medicationMethodがタイミングの薬か時間指定の薬かを判定
    public String isTimingOrSelectedTimeMedicine(USER_MEDICINE medicine) {
        if(medicine.getMedicationMethod().equals("起床時") || medicine.getMedicationMethod().equals("朝食前") ||
            medicine.getMedicationMethod().equals("朝食後") || medicine.getMedicationMethod().equals("昼食前") ||
            medicine.getMedicationMethod().equals("昼食後") || medicine.getMedicationMethod().equals("夕食前") ||
            medicine.getMedicationMethod().equals("夕食後") || medicine.getMedicationMethod().equals("就寝前") ||
            medicine.getMedicationMethod().equals("食間")) {
            return "timing"; // タイミングの薬
        } else if (medicine.getMedicationMethod().matches("^[0-9]{1,2}時$")) {
            return "selectedTime"; // 時間指定の薬
        } else {
            return "error"; // エラー
        }
    }

    // タイミング薬か時間指定薬によって、htmlのvalueに応じた値を返す
    public String getMedicationMethodValue(String medicationMethod, USER_MEDICINE medicine) {
        return switch (medicationMethod) {
            case "timing" -> switch (medicine.getMedicationMethod()) {
                case "起床時" -> "000";
                case "朝食前" -> "001";
                case "朝食後" -> "002";
                case "昼食前" -> "003";
                case "昼食後" -> "004";
                case "夕食前" -> "005";
                case "夕食後" -> "006";
                case "就寝前" -> "007";
                case "食間" -> "008";
                default -> "error";
            };
            case "selectedTime" -> switch (medicine.getMedicationMethod()) {
                case "0時" -> "100";
                case "1時" -> "101";
                case "2時" -> "102";
                case "3時" -> "103";
                case "4時" -> "104";
                case "5時" -> "105";
                case "6時" -> "106";
                case "7時" -> "107";
                case "8時" -> "108";
                case "9時" -> "109";
                case "10時" -> "110";
                case "11時" -> "111";
                case "12時" -> "112";
                case "13時" -> "113";
                case "14時" -> "114";
                case "15時" -> "115";
                case "16時" -> "116";
                case "17時" -> "117";
                case "18時" -> "118";
                case "19時" -> "119";
                case "20時" -> "120";
                case "21時" -> "121";
                case "22時" -> "122";
                case "23時" -> "123";
                default -> "error";
            };
            default -> "error";
        };
    }
}