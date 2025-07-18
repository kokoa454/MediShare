package com.medishare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medishare.model.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
    List<Medicine> findAll();
}
