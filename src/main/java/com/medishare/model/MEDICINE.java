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

    @OneToOne
    @JoinColumn(
            name = "medicineOfficialName",                  // このテーブル側の列名
            referencedColumnName = "medicineOfficialName",  // 参照先のカラム名
            nullable = false,
            unique = true                                     // 1対1制約をつけるために unique を追加
    )
    private MEDICINE medicine;

    private String urlKusurinoShiori;

}