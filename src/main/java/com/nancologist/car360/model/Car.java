package com.nancologist.car360.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

// TODO: Refactor - if @Column(s) are unnecessary - remove them in all entities as much as possible

@Getter
@ToString
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * VIN: Vehicle Identification Number
     */
    @Column(name = "vin", unique = true)
    private String vin;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "body_style_code", columnDefinition = "VARCHAR(64)")
    private String bodyStyleCode;

    @Column(name = "steering", columnDefinition = "VARCHAR(3)")
    private String steering;

    @Column(name = "facelift", columnDefinition = "BOOLEAN")
    private boolean facelift;

    @Column(name = "production_date", columnDefinition = "DATE")
    private Date productionDate;

    @OneToOne()
    @JoinColumn(name = "color_code", referencedColumnName = "code", columnDefinition = "VARCHAR(10)")
    private Color color;

    @OneToOne()
    @JoinColumn(name = "upholstery_code", referencedColumnName = "code", columnDefinition = "VARCHAR(10)")
    private Upholstery upholstery;

    // Engine Specifications:

    @Column(name = "power_in_kw")
    private short powerInKw;

    @Column(name = "displacement_in_liter", columnDefinition = "REAL")
    private float displacementInLiter;

    // Todo: add fuel, cylinders, cylinders form later...

    @Column(name = "doors_count")
    private short doorsCount;

    @Column(name = "transmission", columnDefinition = "VARCHAR(64)")
    private String transmission;

    @Column(name = "drive", columnDefinition = "VARCHAR(64)")
    private String drive;

    @Column(name = "equipment_codes", columnDefinition = "VARCHAR(64)[]")
    private List<String> equipmentCodes;
}
