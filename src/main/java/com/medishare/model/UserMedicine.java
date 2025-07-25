package com.medishare.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "user_medicine")
@Entity
@Getter
@Setter
@ToString
public class UserMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userMedicineId;

    // 🔗 外部キー（USER_DATABASEのuserIdと紐づけ）
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column
    private String medicineOfficialName;

    @Column
    private String urlKusurinoshiori;

    @Column
    private String medicineUserInput;

    @Column
    private String prescriptionDays;

    @Column
    private String medicationMethod;

    @Column
    private String userComment;

    @Column
    private boolean isCompleted;

    @Column
    private String completedDate;

    public UserMedicine() {
    }

    public UserMedicine(
        User user, 
        String medicineUserInput, 
        String medicineOfficialName, 
        String urlKusurinoshiori,
        String prescriptionDays,
        String medicationMethod, 
        String userComment,
        boolean isCompleted,
        String completedDate
        ) {
        this.user = user;
        this.medicineUserInput = medicineUserInput;
        this.medicineOfficialName = medicineOfficialName;
        this.urlKusurinoshiori = urlKusurinoshiori;
        this.prescriptionDays = prescriptionDays;
        this.medicationMethod = medicationMethod;
        this.userComment = userComment;
        this.isCompleted = isCompleted;
        this.completedDate = completedDate;
    }
}
