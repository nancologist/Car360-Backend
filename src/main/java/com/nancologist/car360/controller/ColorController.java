package com.nancologist.car360.controller;

import com.nancologist.car360.dto.ColorDto;
import com.nancologist.car360.dto.ColorOption;
import com.nancologist.car360.service.ColorService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/colors")
public class ColorController {

    private final ColorService colorService;

    @PermitAll()
    @GetMapping()
    public ResponseEntity<List<ColorOption>> getAllColorOptions() {
        return ResponseEntity.ok(colorService.findAllColors());
    }
}
