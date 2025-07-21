package com.medishare.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "official_medicine")
@Entity
@Getter
@Setter
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medicineId;

    @Column(unique = true)
    private String medicineOfficialName;

    @Column(unique = true)
    private String urlKusurinoshiori;

    public Medicine(){

    }
    public Medicine(String medicineOfficialName,String urlKusurinoshiori){
        this.medicineOfficialName = medicineOfficialName;
        this.urlKusurinoshiori = urlKusurinoshiori;
    }

}
