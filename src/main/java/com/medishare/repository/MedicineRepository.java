package com.medishare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medishare.model.MEDICINE;

public interface MedicineRepository extends JpaRepository<MEDICINE, Integer> {
    List<MEDICINE> findAll();
}
