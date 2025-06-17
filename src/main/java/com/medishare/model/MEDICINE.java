package com.medishare.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "medicine")
@Entity
@Getter
@Setter
public class MEDICINE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medicineId;

    @Column(unique = true, nullable = false)
    public String medicineOfficialName;

    @Column(unique = true ,nullable = false)
    private String urlKusurinoShiori;

    public MEDICINE(){

    }
    public MEDICINE(String medicineOfficialName, int medicineId, String urlKusurinoShiori){
        this.medicineOfficialName = medicineOfficialName;
        this.medicineId = medicineId;
        this.urlKusurinoShiori = urlKusurinoShiori;
    }

}