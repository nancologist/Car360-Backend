package com.nancologist.car360.controller;

import com.nancologist.car360.dto.LoginRequest;
import com.nancologist.car360.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/login")
public class LoginController {

    @PostMapping()
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse res = new LoginResponse();
        if (loginRequest.getPassword().equals("456")) {
            res.setMessage("Login successful!");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        res.setMessage("Login failed!");
        return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
    }
}
