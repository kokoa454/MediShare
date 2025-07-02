package com.medishare.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.medishare.dto.TimingGroupDTO;
import com.medishare.model.USER_DATABASE;
import com.medishare.model.USER_MEDICINE;
import com.medishare.repository.UserMedicineRepository;
import com.medishare.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final UserMedicineRepository userMedicineRepository;
    private final UserRepository userRepository;

    public void registerMedicine(
            String medicineUserInput,
            String medicineOfficialName,
            String prescriptionDays,
            String userComment,
            String medicationMethod
    ) {
        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = userData.getName(); // ログインユーザーのメールアドレスを取得
        USER_DATABASE user = userRepository.findByUserEmail(userEmail); // メールアドレスでユーザーを検索

        boolean isCompleted = false;

        // エンティティに詰めて保存
        USER_MEDICINE medicine = new USER_MEDICINE(
                user,
                medicineUserInput,
                medicineOfficialName,
                prescriptionDays,
                medicationMethod,
                userComment,
                isCompleted
        );

        userMedicineRepository.save(medicine);
    }
    
    // 時間帯の薬のみをフィルタリングし、グループ化
    public List<TimingGroupDTO> getTimingMedicinesByUser(int userId){
        List<USER_MEDICINE> allMedicines = userMedicineRepository.findByUserUserIdOrderByUserMedicineIdAsc(userId);
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
        List<USER_MEDICINE> allMedicines = userMedicineRepository.findByUserUserIdOrderByUserMedicineIdAsc(userId);
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
        return  userMedicineRepository.findByUserUserIdAndMedicationMethod(userId, medicationMethod);
    }

    // USER_MEDICINEのuserMedicineIdで薬を取得
    public USER_MEDICINE getMedicineDetailsByUserMedicineId(int userMedicineId) {
        return userMedicineRepository.findByUserMedicineId(userMedicineId);
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

    // 薬の情報を更新
    public void updateMedicine(
            int userMedicineId,
            String medicineUserInput,
            String medicineOfficialName,
            String prescriptionDays,
            String userComment,
            String medicationMethod
    ) {
        USER_MEDICINE medicine = userMedicineRepository.findByUserMedicineId(userMedicineId);
        if (medicine != null) {
            medicine.setMedicineUserInput(medicineUserInput);
            medicine.setMedicineOfficialName(medicineOfficialName);
            medicine.setPrescriptionDays(prescriptionDays);
            medicine.setUserComment(userComment);
            medicine.setMedicationMethod(medicationMethod);
            userMedicineRepository.save(medicine);
        }
    }

    // 薬を削除
    public void deleteMedicine(List<Integer> userMedicineIds) {
        for (Integer userMedicineId : userMedicineIds) {
            userMedicineRepository.deleteById(userMedicineId);
        }
    }

    // userMedicineIdで薬を取得
    public String getMedicineUserInputByUserMedicineId(int userMedicineId) {
        return userMedicineRepository.findByUserMedicineId(userMedicineId).getMedicineUserInput();
    }

    //キーワードで薬を検索
    public List<USER_MEDICINE> getMedicineListByUserAndMedicationMethodAndSearch(int userId, String medicationMethod, String searchKeyword) {
        return userMedicineRepository.findByUserUserIdAndMedicationMethodAndMedicineUserInputContaining(userId, medicationMethod, searchKeyword);
    }

    public void completeMedicine(int userMedicineId) {
        USER_MEDICINE medicine = userMedicineRepository.findByUserMedicineId(userMedicineId);
        medicine.setCompleted(true);
        userMedicineRepository.save(medicine);
    }
}