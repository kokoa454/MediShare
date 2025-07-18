package com.medishare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medishare.model.UserTimetable;

@Repository
public interface UserTimetableRepository extends JpaRepository<UserTimetable, Integer>{
    // ユーザーIDでユーザーを検索する
    UserTimetable findByUserUserId(int userId);
}
