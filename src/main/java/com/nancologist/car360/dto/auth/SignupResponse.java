package com.nancologist.car360.dto.auth;

import lombok.Getter;

@Getter
public class SignupResponse {
    private String token;
    private String userId;
    private String username;
    private String email;
}
