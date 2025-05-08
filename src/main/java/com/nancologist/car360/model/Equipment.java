package com.nancologist.car360.model;

import jakarta.persistence.*;

@Entity
@Table(name = "equipments")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "description_de", nullable = true, columnDefinition = "TEXT")
    private String descriptionDe;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionDe() {
        return descriptionDe;
    }

    public String getManufacturer() {
        return manufacturer;
    }
}
