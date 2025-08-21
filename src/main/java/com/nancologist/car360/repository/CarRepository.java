package com.nancologist.car360.repository;

import com.nancologist.car360.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query(
            value = "SELECT * FROM cars c WHERE c.equipment_codes @> :equipments",
            nativeQuery = true
    )
    List<Car> findCarsWithAllEquipments(@Param("equipments") String[] equipmentCodes);
}


