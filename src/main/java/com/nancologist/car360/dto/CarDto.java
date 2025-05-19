package com.nancologist.car360.dto;

import com.nancologist.car360.model.Car;
import com.nancologist.car360.model.Equipment;
import com.nancologist.car360.model.Upholstery;

import java.util.Date;
import java.util.List;

public class CarDto {

    private final Long id;
    private final String vin;
    private final String manufacturer;
    private final String model;
    private final String bodyStyleCode;
    private final String steering;
    private final boolean facelift;
    private final Date productionDate;
    private final ColorDto colorDto;
    private final Upholstery upholstery;
    private final int powerInKw;
    private final float displacementInLiter;
    private final int doorsCount;
    private final String transmission;
    private final String drive;
    private final List<Equipment> equipments;

    public CarDto(Car car, List<Equipment> equipments) {
        this.id = car.getId();
        this.vin = car.getVin();
        this.manufacturer = car.getManufacturer();
        this.model = car.getModel();
        this.bodyStyleCode = car.getBodyStyleCode();
        this.steering = car.getSteering();
        this.facelift = car.isFacelift();
        this.productionDate = car.getProductionDate();
        this.colorDto = new ColorDto(car.getColor());
        this.upholstery = car.getUpholstery();
        this.powerInKw = car.getPowerInKw();
        this.displacementInLiter = car.getDisplacementInLiter();
        this.doorsCount = car.getDoorsCount();
        this.transmission = car.getTransmission();
        this.drive = car.getDrive();
        this.equipments = equipments;
    }

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

   public ColorDto getColor() {
       return colorDto;
   }

   public Upholstery getUpholstery() {
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

    public List<Equipment> getEquipments() {
        return equipments;
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
