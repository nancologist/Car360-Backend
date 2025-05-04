package com.nancologist.my_rest_app.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Explicitly naming the column is good practice
    private Long id;

    /** VIN: Vehicle Identification Number */
    @Column(name = "vin", unique = true)
    private String vin;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "production_date", columnDefinition = "DATE")
    private Date productionDate;

    @Column(name = "color", columnDefinition = "TEXT")
    private String color;

    @Column(name = "upholstery", columnDefinition = "TEXT")
    private String upholstery;

    // Engine Specifications:

    @Column(name = "power_in_kw", columnDefinition = "SMALLINT")
    private int powerInKw;

    @Column(name = "displacement_in_liter", columnDefinition = "REAL")
    private float displacementInLiter;

    // Todo: add fuel, cylinders, cylinders form later...

    @Column(name = "doors_count", columnDefinition = "SMALLINT")
    private String doorsCount;

    @Column(name = "transmission", columnDefinition = "VARCHAR(64)")
    private String transmission;

    @Column(name = "drive", columnDefinition = "VARCHAR(64)")
    private String drive;
}
