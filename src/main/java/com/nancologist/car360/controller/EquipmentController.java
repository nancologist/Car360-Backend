package com.nancologist.car360.controller;

import com.nancologist.car360.dto.EquipmentDto;
import com.nancologist.car360.service.EquipmentService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/equipments")
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PermitAll
    @GetMapping()
    public ResponseEntity<List<EquipmentDto>> getAllEquipments() {
        return ResponseEntity.ok(equipmentService.findAllEquipments());
    }

    @PermitAll
    @GetMapping("/search")
    public ResponseEntity<List<EquipmentDto>> searchEquipments(@RequestParam String search) {
        return ResponseEntity.ok(equipmentService.searchEquipments(search));
    }
}
