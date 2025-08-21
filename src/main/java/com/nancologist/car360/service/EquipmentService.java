package com.nancologist.car360.service;

import com.nancologist.car360.dto.EquipmentDto;
import com.nancologist.car360.model.Equipment;
import com.nancologist.car360.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public List<EquipmentDto> searchEquipments(String term) {
        List<Equipment> equipments = this.equipmentRepository.findByDescriptionContainingIgnoreCase(term);
        return equipments.stream().map(equipment -> new EquipmentDto(
                equipment.getId(),
                equipment.getCode(),
                equipment.getDescription()
        )).toList();
    }
}
