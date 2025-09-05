package com.nancologist.car360.controller;

import com.nancologist.car360.dto.UpholsteryOption;
import com.nancologist.car360.service.UpholsteryService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/upholsteries")
public class UpholsteryController {

    private final UpholsteryService upholsteryService;

    @PermitAll()
    @GetMapping()
    public ResponseEntity<List<UpholsteryOption>> getAllUpholsteryOptions() {
        return ResponseEntity.ok(upholsteryService.findAllUpholsteries());
    }
}
