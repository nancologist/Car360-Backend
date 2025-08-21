package com.nancologist.car360.repository;

import com.nancologist.car360.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    @Query("SELECT e FROM Equipment e WHERE e.code IN ?1")
    List<Equipment> findByCodeIn(List<String> codes);

    List<Equipment> findByDescriptionContainingIgnoreCase(String term);
}
