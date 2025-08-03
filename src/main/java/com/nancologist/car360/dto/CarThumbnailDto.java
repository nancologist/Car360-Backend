package com.nancologist.car360.dto;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class CarThumbnailDto {
    private final Long carId;
    private final String name;
    private final Date productionDate;
    private final String color;

    public CarThumbnailDto(Long carId,
                           String manufacturer,
                           String bodyStyleCode,
                           String model,
                           String color,
                           Date productionDate) {
        this.carId = carId;
        this.name = String.format("%s - %s %s", manufacturer, bodyStyleCode, model);
        this.productionDate = productionDate;
        this.color = color;
    }

    public String getProductionDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(productionDate);
    }
}
