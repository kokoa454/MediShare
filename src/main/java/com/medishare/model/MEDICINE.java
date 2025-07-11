package com.medishare.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(unique = true)
    private String medicineOfficialName;

    @Column(unique = true)
    private String urlKusurinoShiori;

    public MEDICINE(){

    }
    public MEDICINE(String medicineOfficialName,String urlKusurinoShiori){
        this.medicineOfficialName = medicineOfficialName;
        this.urlKusurinoShiori = urlKusurinoShiori;
    }

}