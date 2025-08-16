package com.nancologist.car360.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@AllArgsConstructor
@Getter
public class LoginResponse {

    @Schema(requiredMode = REQUIRED)
    private String token;

    @Schema(requiredMode = REQUIRED)
    private Long userId;

    @Schema(requiredMode = REQUIRED)
    private String username;

    @Schema(requiredMode = REQUIRED)
    private String email;
}
