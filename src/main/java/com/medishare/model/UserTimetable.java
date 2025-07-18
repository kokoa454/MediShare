package com.medishare.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "user_timetable")
@Entity
@Getter
@Setter
@ToString
public class UserTimetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTimetableId;

    // 🔗 外部キー（USER_DATABASEのuserIdと紐づけ）
    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = true)
    private String wakeUp;

    @Column(nullable = true)
    private String beforeBreakfast;

    @Column(nullable = true)
    private String afterBreakfast;

    @Column(nullable = true)
    private String beforeLunch;

    @Column(nullable = true)
    private String afterLunch;

    @Column(nullable = true)
    private String beforeDinner;

    @Column(nullable = true)
    private String afterDinner;

    @Column(nullable = true)
    private String beforeSleep;

    @Column(nullable = true)
    private String betweenMeals;

    public UserTimetable(){}

    public UserTimetable(
        User user,
        String wakeUp,
        String beforeBreakfast,
        String afterBreakfast,
        String beforeLunch,
        String afterLunch,
        String beforeDinner,
        String afterDinner,
        String beforeSleep,
        String betweenMeals
    ){
        this.user = user;
        this.wakeUp = wakeUp;
        this.beforeBreakfast = beforeBreakfast;
        this.afterBreakfast = afterBreakfast;
        this.beforeLunch = beforeLunch;
        this.afterLunch = afterLunch;
        this.beforeDinner = beforeDinner;
        this.afterDinner = afterDinner;
        this.beforeSleep = beforeSleep;
        this.betweenMeals = betweenMeals;
    }
}
