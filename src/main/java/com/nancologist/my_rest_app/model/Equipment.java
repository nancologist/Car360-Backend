package com.nancologist.my_rest_app.model;

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
}
