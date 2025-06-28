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
public class USER_MEDICINE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userMedicineId;

    // 🔗 外部キー（USER_DATABASEのuserIdと紐づけ）
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private USER_DATABASE user;

    @Column(nullable = false)
    private String medicineOfficialName;

    @Column
    private String medicineUserInput;

    @Column
    private String prescriptionDays;

    @Column
    private String medicationMethod;

    @Column
    private String userComment;

    public USER_MEDICINE() {
    }

    public USER_MEDICINE(
        USER_DATABASE user, 
        String medicineUserInput, 
        String medicineOfficialName, 
        String prescriptionDays,
        String medicationMethod, 
        String userComment
        ) {
        this.user = user;
        this.medicineUserInput = medicineUserInput;
        this.medicineOfficialName = medicineOfficialName;
        this.prescriptionDays = prescriptionDays;
        this.medicationMethod = medicationMethod;
        this.userComment = userComment;
    }
}
