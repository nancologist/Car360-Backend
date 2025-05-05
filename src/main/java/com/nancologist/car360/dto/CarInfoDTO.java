package com.nancologist.car360.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CarInfoDTO {
    private final Long carId;
    private final String name;
    private final Date productionDate;
    private final String color;

    public CarInfoDTO(Long carId,
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

    public Long getCarId() {
        return carId;
    }

    public String getName() {
        return name;
    }

    public String getProductionDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(productionDate);
    }

    public String getColor() {
        return color;
    }
}
