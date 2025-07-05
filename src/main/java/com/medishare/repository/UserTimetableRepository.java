package com.medishare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medishare.model.USER_TIMETABLE;

@Repository
public interface UserTimetableRepository extends JpaRepository<USER_TIMETABLE, Integer>{
    // ユーザーIDでユーザーを検索する
    USER_TIMETABLE findByUserUserId(int userId);
}
