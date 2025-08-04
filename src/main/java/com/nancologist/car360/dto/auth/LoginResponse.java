package com.nancologist.car360.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponse {
    private String token;
    private Long userId;
    private String username;
    private String email;
}
