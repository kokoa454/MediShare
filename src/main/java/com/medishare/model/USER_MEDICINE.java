package com.medishare.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "user_medicine")
@Entity
@Getter
@Setter
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
