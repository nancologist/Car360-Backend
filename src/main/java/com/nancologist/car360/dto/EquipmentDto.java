package com.nancologist.car360.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@AllArgsConstructor
@Getter
public class EquipmentDto {

    @Schema(requiredMode = REQUIRED)
    private Long id;

    @Schema(requiredMode = REQUIRED)
    private String code;

    @Schema(requiredMode = REQUIRED)
    private String description;
}
