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

import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> errRes = new HashMap<>();
        errRes.put("message", "Login failed!!!");
        return new ResponseEntity<>(errRes, HttpStatus.UNAUTHORIZED);
    }
}
