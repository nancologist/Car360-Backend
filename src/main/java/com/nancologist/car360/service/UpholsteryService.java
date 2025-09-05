package com.nancologist.car360.service;

import com.nancologist.car360.dto.UpholsteryOption;
import com.nancologist.car360.repository.UpholsteryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UpholsteryService {

    private final UpholsteryRepository upholsteryRepository;

    public List<UpholsteryOption> findAllUpholsteries() {
        return this.upholsteryRepository.findAllByOrderByName().stream().map(upholstery -> new UpholsteryOption(
                upholstery.getId(),
                upholstery.getCode(),
                upholstery.getName()
        )).toList();
    }
}
