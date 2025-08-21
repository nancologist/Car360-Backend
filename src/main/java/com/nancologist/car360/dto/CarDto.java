package com.nancologist.car360.dto;

import com.nancologist.car360.model.Car;
import com.nancologist.car360.model.Equipment;
import com.nancologist.car360.model.Upholstery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Date;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
public class CarDto {

    @Schema(requiredMode = REQUIRED)
    private final Long id;

    @Schema(requiredMode = REQUIRED)
    private final String vin;

    @Schema(requiredMode = REQUIRED)
    private final String manufacturer;

    @Schema(requiredMode = REQUIRED)
    private final String model;

    @Schema(requiredMode = REQUIRED)
    private final String bodyStyleCode;

    @Schema(requiredMode = REQUIRED)
    private final String steering;

    @Schema(requiredMode = REQUIRED)
    private final boolean facelift;

    @Schema(requiredMode = REQUIRED)
    private final Date productionDate;

    @Schema(requiredMode = REQUIRED)
    private final ColorDto colorDto;

    @Schema(requiredMode = REQUIRED)
    private final Upholstery upholstery;

    @Schema(requiredMode = REQUIRED)
    private final int powerInKw;

    @Schema(requiredMode = REQUIRED)
    private final float displacementInLiter;

    @Schema(requiredMode = REQUIRED)
    private final int doorsCount;

    @Schema(requiredMode = REQUIRED)
    private final String transmission;

    @Schema(requiredMode = REQUIRED)
    private final String drive;

    @Schema(requiredMode = REQUIRED)
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
}
