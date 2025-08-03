package com.nancologist.car360.dto;

import com.nancologist.car360.model.Color;
import lombok.Getter;

@Getter
public class ColorDto {
    private final Long id;
    private final String code;
    private final String name;
    private final String manufacturer;

    public ColorDto(Color color) {
        this.id = color.getId();
        this.code = color.getCode();
        this.name = color.getName();
        this.manufacturer = color.getManufacturer();
    }

}
