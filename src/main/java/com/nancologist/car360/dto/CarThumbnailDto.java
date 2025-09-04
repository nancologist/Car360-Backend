package com.nancologist.car360.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
public class CarThumbnailDto {

    @Schema(requiredMode = REQUIRED)
    private final Long carId;

    @Schema(requiredMode = REQUIRED)
    private final String name;

    @Schema(requiredMode = REQUIRED)
    private final Date productionDate;

    @Schema(requiredMode = REQUIRED)
    private final String color;

    @Schema(requiredMode = REQUIRED)
    private final List<String> equipmentCodes;

    public CarThumbnailDto(Long carId,
                           String manufacturer,
                           String bodyStyleCode,
                           String model,
                           String color,
                           Date productionDate,
                           List<String> equipmentCodes) {
        this.carId = carId;
        this.name = String.format("%s - %s %s", manufacturer, bodyStyleCode, model);
        this.productionDate = productionDate;
        this.color = color;
        this.equipmentCodes = equipmentCodes;
    }

    public String getProductionDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(productionDate);
    }
}
