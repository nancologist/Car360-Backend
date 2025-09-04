package com.nancologist.car360.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ColorOption {

    @Schema(requiredMode =  Schema.RequiredMode.REQUIRED)
    private final Long id;
    @Schema(requiredMode =  Schema.RequiredMode.REQUIRED)
    private final String description;

    public ColorOption(Long colorId, String colorCode, String colorName) {
        this.id = colorId;
        this.description = String.format("%s (%s)", colorName, colorCode);
    }
}
