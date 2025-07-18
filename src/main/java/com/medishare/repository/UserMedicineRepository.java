package com.medishare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medishare.model.UserMedicine;

@Repository
public interface UserMedicineRepository extends JpaRepository<UserMedicine, Integer> {
    // ユーザーごとの薬をすべて取得(userIdで昇順ソート)
    List<UserMedicine> findByUserUserIdOrderByUserMedicineIdAsc(int userId);

    // ユーザーIDと投薬方法でフィルタリング
    List<UserMedicine> findByUserUserIdAndMedicationMethod(int userId, String medicationMethod);

    //userMedicineIdで薬を取得
    UserMedicine findByUserMedicineId(int userMedicineId);
    
    // ユーザーIDと投薬方法と薬名でフィルタリング
    List<UserMedicine> findByUserUserIdAndMedicationMethodAndMedicineUserInputContaining(int userId, String medicationMethod, String medicineUserInput);
}

