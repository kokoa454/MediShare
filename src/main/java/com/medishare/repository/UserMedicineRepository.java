package com.medishare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medishare.model.USER_MEDICINE;

@Repository
public interface UserMedicineRepository extends JpaRepository<USER_MEDICINE, Integer> {

    // ユーザーごとの薬をすべて取得(userIdで昇順ソート)
    List<USER_MEDICINE> findByUserUserIdOrderByUserMedicineIdAsc(int userId);
}

