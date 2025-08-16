package com.nancologist.car360.dto;

import com.nancologist.car360.model.Color;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
public class ColorDto {

    @Schema(requiredMode = REQUIRED)
    private final Long id;

    @Schema(requiredMode = REQUIRED)
    private final String code;

    @Schema(requiredMode = REQUIRED)
    private final String name;

    @Schema(requiredMode = REQUIRED)
    private final String manufacturer;

    public ColorDto(Color color) {
        this.id = color.getId();
        this.code = color.getCode();
        this.name = color.getName();
        this.manufacturer = color.getManufacturer();
    }

}
