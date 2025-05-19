package com.nancologist.car360.dto;

import com.nancologist.car360.model.Color;

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

    public Long getId() {
        return id;
    }
    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }
}
