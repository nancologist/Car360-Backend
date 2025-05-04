package com.nancologist.my_rest_app.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

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
    private int doorsCount;

    @Column(name = "transmission", columnDefinition = "VARCHAR(64)")
    private String transmission;

    @Column(name = "drive", columnDefinition = "VARCHAR(64)")
    private String drive;

    @Column(name = "equipment_codes", columnDefinition = "VARCHAR(64)[]")
    private List<String> equipmentCodes;

    public Long getId() {
        return id;
    }

    public String getVin() {
        return vin;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public String getColor() {
        return color;
    }

    public String getUpholstery() {
        return upholstery;
    }

    public int getPowerInKw() {
        return powerInKw;
    }

    public float getDisplacementInLiter() {
        return displacementInLiter;
    }

    public int getDoorsCount() {
        return doorsCount;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getDrive() {
        return drive;
    }

    public List<String> getEquipmentCodes() {
        return equipmentCodes;
    }

    public String getBodyStyleCode() {
        return bodyStyleCode;
    }

    public String getSteering() {
        return steering;
    }

    public boolean isFacelift() {
        return facelift;
    }
}
