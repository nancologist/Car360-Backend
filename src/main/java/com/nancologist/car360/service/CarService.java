package com.nancologist.car360.service;

import com.nancologist.car360.dto.CarDTO;
import com.nancologist.car360.model.Car;
import com.nancologist.car360.model.Equipment;
import com.nancologist.car360.repository.CarRepository;
import com.nancologist.car360.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final EquipmentRepository equipmentRepository;

    @Autowired
    public CarService(CarRepository carRepo, EquipmentRepository eqRepo) {
        this.carRepository = carRepo;
        this.equipmentRepository = eqRepo;
    }

    public CarDTO getCarById(Long id) {
        Car car = this.carRepository.getReferenceById(id);
        System.out.println(car.getEquipmentCodes());
        List<Equipment> carEquipments = this.equipmentRepository.findByCodeIn(car.getEquipmentCodes());
        return new CarDTO(car, carEquipments);
    }
}
