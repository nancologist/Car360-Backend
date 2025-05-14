package com.nancologist.car360.model;

import jakarta.persistence.*;

@Entity
@Table(name = "upholsteries")
public class Upholstery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", columnDefinition = "VARCHAR(10)")
    private String code;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;
}
