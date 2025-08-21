package com.nancologist.car360.controller;

import com.nancologist.car360.dto.CarDto;
import com.nancologist.car360.dto.CarThumbnailDto;
import com.nancologist.car360.service.CarService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Todo: add global exception handler (for error responses)

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    @PermitAll
    @GetMapping("/{id}/color-image")
    public ResponseEntity<byte[]> getCarColorImage(@PathVariable("id") Long carId) {
        byte[] imageBytes = carService.getCarColorImage(carId);

        if (imageBytes == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageBytes.length);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @PermitAll
    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable("id") Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @PermitAll
    @GetMapping
    public ResponseEntity<List<CarThumbnailDto>> getCarThumbnails(
            @RequestParam(required = false) String[] equipmentCodes
    ) {
        // Todo: Add SearchFilterService to create Map<String, Object> Filters so the key is filterName e.g.
        //  "selectedEquipments" and the value is EquipmentDto[]
        List<CarThumbnailDto> result = this.carService.getCarThumbnails(equipmentCodes);
        return ResponseEntity.ok(result);
    }
}
