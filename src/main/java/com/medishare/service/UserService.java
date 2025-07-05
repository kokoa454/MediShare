package com.medishare.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medishare.model.USER_DATABASE;
import com.medishare.model.USER_TIMETABLE;
import com.medishare.repository.UserRepository;
import com.medishare.repository.UserTimetableRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTimetableRepository userTimetableRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserTimetableRepository userTimetableRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userTimetableRepository = userTimetableRepository;
    }

    // ユーザー登録処理
    public boolean registerUser(String userEmail, String password) {
        if (userRepository.existsByUserEmail(userEmail)) {
            return false;  // すでに登録済み
        }

        String encodedPassword = passwordEncoder.encode(password);
        USER_DATABASE user = new USER_DATABASE(userEmail, encodedPassword, null, null, null);
        USER_TIMETABLE timetable = new USER_TIMETABLE(user, "07:00", "08:00", "09:00", "11:30", "13:00", "18:00", "19:30", "22:00", "15:00");
        userRepository.save(user);
        userTimetableRepository.save(timetable);
        return true;
    }

    // ログインユーザーのIDを取得
    public int getLoginUserId(){
        USER_DATABASE user = userRepository.findByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given email.");
        }
        return user.getUserId();
    }

    // ユーザーIDから家族メールアドレスを取得
    public String getFamilyEmail(int userId){
        USER_DATABASE user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return user.getFamilyEmail();
    }

    // ユーザIDからユーザメールアドレスを更新
    public void updateUserEmail(int userId, String userEmail){
        USER_DATABASE user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        user.setUserEmail(userEmail);
        userRepository.save(user);
    }

    //ユーザIDから家族メールアドレスを更新
    public void updateFamilyEmail(int userId, String familyEmail){
        USER_DATABASE user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        user.setFamilyEmail(familyEmail);
        userRepository.save(user);
    }

    // ユーザIDからユーザ名を取得
    public String getUserName(int userId){
        USER_DATABASE user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return user.getUserName();
    }

    // ユーザIDからユーザ名を更新
    public void updateUserName(int userId, String userName){
        USER_DATABASE user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        user.setUserName(userName);
        userRepository.save(user);
    }

    // ユーザIDから家族のLINEIDを取得
    public String getFamilyLineId(int userId){
        USER_DATABASE user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return user.getFamilyLineId();
    }

    // ユーザIDから家族のLINEIDを更新
    public void updateFamilyLineId(int userId, String familyLineId){
        USER_DATABASE user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        user.setFamilyLineId(familyLineId);
        userRepository.save(user);
    }
}
