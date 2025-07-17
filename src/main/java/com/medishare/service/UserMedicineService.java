package com.medishare.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.medishare.dto.TimeZoneGroupDTO;
import com.medishare.model.USER_DATABASE;
import com.medishare.model.USER_MEDICINE;
import com.medishare.repository.UserMedicineRepository;
import com.medishare.repository.UserRepository;
import com.medishare.model.TimeZoneCategory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMedicineService {

    private final UserMedicineRepository userMedicineRepository;
    private final UserRepository userRepository;

    // 薬を登録
    public void registerMedicine(
            String medicineUserInput,
            String medicineOfficialName,
            String urlKusurinoshiori,
            String prescriptionDays,
            String userComment,
            String medicationMethod
    ) {
        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = userData.getName(); // ログインユーザーのメールアドレスを取得
        USER_DATABASE user = userRepository.findByUserEmail(userEmail); // メールアドレスでユーザーを検索

        boolean isCompleted = false;
        String completedDate = null;

        // エンティティに詰めて保存
        USER_MEDICINE medicine = new USER_MEDICINE(
                user,
                medicineUserInput,
                medicineOfficialName,
                urlKusurinoshiori,
                prescriptionDays,
                medicationMethod,
                userComment,
                isCompleted,
                completedDate
        );

        userMedicineRepository.save(medicine);
    }
    
    // 時間帯の薬のみをフィルタリングし、グループ化
    public List<TimeZoneGroupDTO> getTimingMedicinesByUser(int userId){
        List<USER_MEDICINE> allMedicines = userMedicineRepository.findByUserUserIdOrderByUserMedicineIdAsc(userId);
        Map<String, List<USER_MEDICINE>> groupedMedicines = allMedicines.stream()
            .filter(medicine -> isTimingMethod(medicine.getMedicationMethod())) // 時間帯の薬のみフィルタリング
            .collect(Collectors.groupingBy(USER_MEDICINE::getMedicationMethod));

        List<TimeZoneGroupDTO> timingMedicines = new ArrayList<>();
        for (Map.Entry<String, List<USER_MEDICINE>> entry : groupedMedicines.entrySet()) {
            timingMedicines.add(new TimeZoneGroupDTO(entry.getKey(), entry.getValue()));
        }

        return timingMedicines;
    }

    // ユーザーの薬を取得して、時間帯ごとにグループ化
    private boolean isTimingMethod(String method) {
        try{
            return TimeZoneCategory.fromLabel(method).isTiming(); // タイミングの薬かどうかを判定
        } catch (IllegalArgumentException e) {
            return false; // タイミングの薬ではない場合
        }
    }

    // 時間指定の薬のみをフィルタリングし、グループ化
    public List<TimeZoneGroupDTO> getSelectedTimeMedicinesByUser(int userId){
        List<USER_MEDICINE> allMedicines = userMedicineRepository.findByUserUserIdOrderByUserMedicineIdAsc(userId);
        Map<String, List<USER_MEDICINE>> groupedMedicines = allMedicines.stream()
            .filter(medicine -> isSelectedTimeMethod(medicine.getMedicationMethod())) // 時間帯の薬のみフィルタリング
            .collect(Collectors.groupingBy(USER_MEDICINE::getMedicationMethod));

        List<TimeZoneGroupDTO> selectedTimeMedicines = new ArrayList<>();
        for (Map.Entry<String, List<USER_MEDICINE>> entry : groupedMedicines.entrySet()) {
            selectedTimeMedicines.add(new TimeZoneGroupDTO(entry.getKey(), entry.getValue()));
        }

        return selectedTimeMedicines;
    }

    // ユーザーの薬を取得して、時間指定ごとにグループ化
    private boolean isSelectedTimeMethod(String method) {
        try {
            return TimeZoneCategory.fromLabel(method).isSelectedTime(); // 時間指定の薬かどうかを判定
        } catch (IllegalArgumentException e) {
            return false; // 時間指定の薬ではない場合
        }
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
        TimeZoneCategory category = TimeZoneCategory.fromLabel(medicine.getMedicationMethod());
        if (category.isTiming()) {
            return "timing";
        } else {
            return "selectedTime";
        }
    }

    // 薬の情報を更新
    public void updateMedicine(
            int userMedicineId,
            String medicineUserInput,
            String medicineOfficialName,
            String urlKusurinoshiori,
            String prescriptionDays,
            String userComment,
            String medicationMethod
    ) {
        USER_MEDICINE medicine = userMedicineRepository.findByUserMedicineId(userMedicineId);
        if (medicine != null) {
            medicine.setMedicineUserInput(medicineUserInput);
            medicine.setMedicineOfficialName(medicineOfficialName);
            medicine.setUrlKusurinoshiori(urlKusurinoshiori);
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

    // userMedicineIdで薬のuserInputを取得
    public String getMedicineUserInputByUserMedicineId(int userMedicineId) {
        return userMedicineRepository.findByUserMedicineId(userMedicineId).getMedicineUserInput();
    }

    //userIdでそのユーザの全薬を取得
    public List<USER_MEDICINE> getAllMedicinesByUserId(int userId) {
        return userMedicineRepository.findByUserUserIdOrderByUserMedicineIdAsc(userId);
    }

    //キーワードで薬を検索
    public List<USER_MEDICINE> getMedicineListByUserAndMedicationMethodAndSearch(int userId, String medicationMethod, String searchKeyword) {
        return userMedicineRepository.findByUserUserIdAndMedicationMethodAndMedicineUserInputContaining(userId, medicationMethod, searchKeyword);
    }

    // 服薬を完了させる
    public void completeMedicine(int userMedicineId, String completedDate) {
        USER_MEDICINE medicine = userMedicineRepository.findByUserMedicineId(userMedicineId);
        medicine.setCompleted(true);
        medicine.setCompletedDate(completedDate);
        int prescriptionDays = Integer.parseInt(medicine.getPrescriptionDays());
        medicine.setPrescriptionDays(String.valueOf(prescriptionDays - 1));
        userMedicineRepository.save(medicine);
    }

    // すべての服薬が完了しているかを判定
    public boolean isAllMedicinesCompleted(int userId, String medicationMethod) {
        List<USER_MEDICINE> medicines = userMedicineRepository.findByUserUserIdAndMedicationMethod(userId, medicationMethod);
        return medicines.stream().allMatch(USER_MEDICINE::isCompleted);
    }

    // 最終服薬時間をチェック
    public void checkLastMedicineTime(int userId, String medicationMethod) {
        List<USER_MEDICINE> medicines = userMedicineRepository.findByUserUserIdAndMedicationMethod(userId, medicationMethod);
        String formattedDate = LocalDate.now().toString();

        for(USER_MEDICINE medicine: medicines) {
            if (medicine.getCompletedDate() != null && !medicine.getCompletedDate().equals(formattedDate)) {
                medicine.setCompleted(false);
                userMedicineRepository.save(medicine);
            }
        }
    }
}