package com.nancologist.car360.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpholsteryOption {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private final Long id;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private final String description;

    public UpholsteryOption(Long upholsteryId, String upholsteryCode, String upholsteryName) {
        this.id = upholsteryId;
        this.description = String.format("%s (%s)", upholsteryName, upholsteryCode);
    }
}
