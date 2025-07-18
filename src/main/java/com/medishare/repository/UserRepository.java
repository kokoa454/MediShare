package com.medishare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.medishare.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // メールアドレスでユーザーを検索する
    User findByUserEmail(String userEmail);

    // ユーザーが存在するか確認
    boolean existsByUserEmail(String userEmail);

    // ユーザーIDでユーザーを検索する
    User findByUserId(int userId);

    // すべてのユーザーIDを取得する
    @Query("SELECT u.userId FROM User u")
    List<Integer> findAllUserIds();
}
