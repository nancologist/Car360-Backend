package com.nancologist.car360.controller;

import com.nancologist.car360.dto.CarInfoDTO;
import com.nancologist.car360.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    CarRepository carRepository;

    @GetMapping
    public List<CarInfoDTO> getAllCars() {
        return carRepository.getAllCarInfo();
    }
}
