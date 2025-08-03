package com.nancologist.car360.controller;

import com.nancologist.car360.dto.CarDto;
import com.nancologist.car360.dto.CarThumbnailDto;
import com.nancologist.car360.repository.CarRepository;
import com.nancologist.car360.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarRepository carRepository;
    private final CarService carService;

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

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable("id") Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @GetMapping
    public ResponseEntity<List<CarThumbnailDto>> getCarThumbnails() {
        return ResponseEntity.ok(carRepository.getCarThumbnails());
    }
}
