package com.nancologist.car360.repository;

import com.nancologist.car360.dto.CarInfoDTO;
import com.nancologist.car360.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("""
        SELECT new com.nancologist.car360.dto.CarInfoDTO(
            c.id,
            c.manufacturer,
            c.bodyStyleCode,
            c.model,
            c.color.code,
            c.productionDate) FROM Car c ORDER BY c.productionDate DESC
    """)
    List<CarInfoDTO> getAllCarInfo();
}
