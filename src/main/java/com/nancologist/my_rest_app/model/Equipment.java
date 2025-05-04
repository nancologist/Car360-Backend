package com.nancologist.my_rest_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "equipments")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String description_de;

    @Column(nullable = false)
    private String manufacturer;
}
