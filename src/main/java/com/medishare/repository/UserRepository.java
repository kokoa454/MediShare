package com.medishare.repository;

import com.medishare.model.USER_DATABASE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<USER_DATABASE, Integer> {

    // メールアドレスでユーザーを検索する
    USER_DATABASE findByUserEmail(String userEmail);

    // ユーザーが存在するか確認
    boolean existsByUserEmail(String userEmail);

}

