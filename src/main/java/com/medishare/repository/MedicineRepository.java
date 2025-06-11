package com.medishare.repository;

import com.medishare.model.USER_MEDICINE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<USER_MEDICINE, Integer> {

    // ユーザーごとの薬をすべて取得（時間帯を使ってグループ分けできるように）
    List<USER_MEDICINE> findByUserUserId(int userId);
}

