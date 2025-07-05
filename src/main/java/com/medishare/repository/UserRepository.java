package com.medishare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.medishare.model.USER_DATABASE;

@Repository
public interface UserRepository extends JpaRepository<USER_DATABASE, Integer> {

    // メールアドレスでユーザーを検索する
    USER_DATABASE findByUserEmail(String userEmail);

    // ユーザーが存在するか確認
    boolean existsByUserEmail(String userEmail);

    // ユーザーIDでユーザーを検索する
    USER_DATABASE findByUserId(int userId);

    // すべてのユーザーIDを取得する
    @Query("SELECT u.userId FROM USER_DATABASE u")
    List<Integer> findAllUserIds();
}
