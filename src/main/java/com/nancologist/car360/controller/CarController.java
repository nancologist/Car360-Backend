package com.nancologist.car360.controller;

import com.nancologist.car360.dto.CarDTO;
import com.nancologist.car360.dto.CarInfoDTO;
import com.nancologist.car360.repository.CarRepository;
import com.nancologist.car360.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarRepository carRepository;
    private final CarService carService;

    @Autowired
    public CarController(CarRepository carRepo, CarService carService) {
        this.carRepository = carRepo;
        this.carService = carService;
    }

    @GetMapping("/{id}")
    public CarDTO getCar(@PathVariable("id") Long id) {
        return carService.getCarById(id);
    }

    @GetMapping
    public List<CarInfoDTO> getAllCars() {
        return carRepository.getAllCarInfo();
    }
}
