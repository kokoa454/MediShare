package com.medishare.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medishare.model.User;
import com.medishare.model.UserTimetable;
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
        User user = new User(userEmail, encodedPassword, null, null, null, null);
        UserTimetable timetable = new UserTimetable(user, "07:00", "08:00", "09:00", "11:30", "13:00", "18:00", "19:30", "22:00", "15:00");
        userRepository.save(user);
        userTimetableRepository.save(timetable);
        return true;
    }

    public int getUserIdByEmail(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given email.");
        }
        return user.getUserId();
    }

    // ログインユーザーのIDを取得
    public int getLoginUserId(){
        User user = userRepository.findByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given email.");
        }
        return user.getUserId();
    }

    // ユーザーIDから家族メールアドレスを取得
    public String getFamilyEmail(int userId){
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return user.getFamilyEmail();
    }

    // ユーザIDからユーザメールアドレスを更新
    public void updateUserEmail(int userId, String userEmail){
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        user.setUserEmail(userEmail);
        userRepository.save(user);
    }

    //ユーザIDから家族メールアドレスを更新
    public void updateFamilyEmail(int userId, String familyEmail){
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        user.setFamilyEmail(familyEmail);
        userRepository.save(user);
    }

    // ユーザIDからユーザ名を取得
    public String getUserName(int userId){
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return user.getUserName();
    }

    // ユーザIDからユーザLINEIDを取得
    public String getUserLineId(int userId){
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return user.getUserLineId();
    }

    // ユーザIDからユーザLINEIDを更新
    public void updateUserLineId(int userId, String userLineId){
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        user.setUserLineId(userLineId);
        userRepository.save(user);
    }

    // すべてのユーザIDを取得
    public List<Integer> getAllUserIds(){
        return userRepository.findAllUserIds();
    }

    // ユーザIDからタイムテーブルを取得
    public UserTimetable getUserTimetableByUserId(int userId){
        return userTimetableRepository.findByUserUserId(userId);
    }

    // ユーザIDからユーザ名を更新
    public void updateUserName(int userId, String userName){
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        user.setUserName(userName);
        userRepository.save(user);
    }

    // ユーザIDから家族のLINEIDを取得
    public String getFamilyLineId(int userId){
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return user.getFamilyLineId();
    }

    // ユーザIDから家族のLINEIDを更新
    public void updateFamilyLineId(int userId, String familyLineId){
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        user.setFamilyLineId(familyLineId);
        userRepository.save(user);
    }

    // ユーザIDから起床時のタイムテーブルを取得
    public String getWakeUpTime(int userId){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return userTimetable.getWakeUp();
    }

    // ユーザIDから起床時のタイムテーブルを更新
    public void updateWakeUpTime(int userId, String wakeUpTime){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        userTimetable.setWakeUp(wakeUpTime);
        userTimetableRepository.save(userTimetable);
    }

    // ユーザIDから朝食前のタイムテーブルを取得
    public String getBeforeBreakfastTime(int userId){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return userTimetable.getBeforeBreakfast();
    }

    // ユーザIDから朝食前のタイムテーブルを更新
    public void updateBeforeBreakfastTime(int userId, String breakfastTime){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        userTimetable.setBeforeBreakfast(breakfastTime);
        userTimetableRepository.save(userTimetable);
    }

    // ユーザIDから朝食後のタイムテーブルを取得
    public String getAfterBreakfastTime(int userId){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return userTimetable.getAfterBreakfast();
    }

    // ユーザIDから朝食後のタイムテーブルを更新
    public void updateAfterBreakfastTime(int userId, String afterBreakfastTime){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        userTimetable.setAfterBreakfast(afterBreakfastTime);
        userTimetableRepository.save(userTimetable);
    }

    // ユーザIDから昼食前のタイムテーブルを取得
    public String getBeforeLunchTime(int userId){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return userTimetable.getBeforeLunch();
    }

    // ユーザIDから昼食前のタイムテーブルを更新
    public void updateBeforeLunchTime(int userId, String lunchTime){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        userTimetable.setBeforeLunch(lunchTime);
        userTimetableRepository.save(userTimetable);
    }

    // ユーザIDから昼食後のタイムテーブルを取得
    public String getAfterLunchTime(int userId){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return userTimetable.getAfterLunch();
    }

    // ユーザIDから昼食後のタイムテーブルを更新
    public void updateAfterLunchTime(int userId, String afterLunchTime){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        userTimetable.setAfterLunch(afterLunchTime);
        userTimetableRepository.save(userTimetable);
    }

    // ユーザIDから夕食前のタイムテーブルを取得
    public String getBeforeDinnerTime(int userId){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return userTimetable.getBeforeDinner();
    }

    // ユーザIDから夕食前のタイムテーブルを更新
    public void updateBeforeDinnerTime(int userId, String dinnerTime){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        userTimetable.setBeforeDinner(dinnerTime);
        userTimetableRepository.save(userTimetable);
    }

    // ユーザIDから夕食後のタイムテーブルを取得
    public String getAfterDinnerTime(int userId){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return userTimetable.getAfterDinner();
    }

    // ユーザIDから夕食後のタイムテーブルを更新
    public void updateAfterDinnerTime(int userId, String afterDinnerTime){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        userTimetable.setAfterDinner(afterDinnerTime);
        userTimetableRepository.save(userTimetable);
    }

    // ユーザIDから就寝前のタイムテーブルを取得
    public String getBeforeSleepTime(int userId){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return userTimetable.getBeforeSleep();
    }

    // ユーザIDから就寝前のタイムテーブルを更新
    public void updateBeforeSleepTime(int userId, String beforeSleepTime){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        userTimetable.setBeforeSleep(beforeSleepTime);
        userTimetableRepository.save(userTimetable);
    }

    // ユーザIDから食間のタイムテーブルを取得
    public String getBetweenMealsTime(int userId){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        return userTimetable.getBetweenMeals();
    }

    // ユーザIDから食間のタイムテーブルを更新
    public void updateBetweenMealsTime(int userId, String betweenMealsTime){
        UserTimetable userTimetable = userTimetableRepository.findByUserUserId(userId);
        if (userTimetable == null) {
            throw new NoSuchElementException("No such user found with the given id.");
        }
        userTimetable.setBetweenMeals(betweenMealsTime);
        userTimetableRepository.save(userTimetable);
    }
}
