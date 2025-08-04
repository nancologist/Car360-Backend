package com.nancologist.car360.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Email(message = "Email should be valid")
    // Add @Pattern, because @Email does not catch john@example which is possible in local networks but very rare for public use
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Email should be valid"
    )
    private String email;

    @NotBlank
    @Size(min = 6, message = "Password should be at least 6 characters long")
    private String password;
}
