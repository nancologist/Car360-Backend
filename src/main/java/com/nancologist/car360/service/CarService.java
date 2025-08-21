package com.nancologist.car360.service;

import com.nancologist.car360.dto.CarDto;
import com.nancologist.car360.dto.CarThumbnailDto;
import com.nancologist.car360.model.Car;
import com.nancologist.car360.model.Equipment;
import com.nancologist.car360.repository.CarRepository;
import com.nancologist.car360.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;
    private final EquipmentRepository equipmentRepository;

    public List<CarThumbnailDto> getCarThumbnails(String[] equipmentCodes) {

        List<Car> cars;
        if (equipmentCodes != null) {
            cars = carRepository.findCarsWithAllEquipments(equipmentCodes);
        } else {
            cars = carRepository.findAll(Sort.by("productionDate").descending());
        }

        System.out.println("SIZEEE");
        System.out.println(cars.size());

        return cars
                .stream()
                .map(car -> new CarThumbnailDto(
                        car.getId(),
                        car.getManufacturer(),
                        car.getBodyStyleCode(),
                        car.getModel(),
                        car.getColor().getName(),
                        car.getProductionDate()
                ))
                .toList();
    }

    public CarDto getCarById(Long id) {
        Car car = this.carRepository.getReferenceById(id);
        List<Equipment> carEquipments = this.equipmentRepository.findByCodeIn(car.getEquipmentCodes());
        return new CarDto(car, carEquipments);
    }

    public byte[] getCarColorImage(Long carId) {
        Car car = this.carRepository.getReferenceById(carId);
        return car.getColor().getImageBytes();
    }
}
