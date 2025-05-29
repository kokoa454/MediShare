package com.medishare.repository;

import com.medishare.model.USER_MEDICINE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<USER_MEDICINE, Integer> {

}

