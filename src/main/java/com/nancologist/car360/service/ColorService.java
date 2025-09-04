package com.nancologist.car360.service;

import com.nancologist.car360.dto.ColorDto;
import com.nancologist.car360.dto.ColorOption;
import com.nancologist.car360.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ColorService {

    private final ColorRepository colorRepository;

    public List<ColorOption> findAllColors() {
        return this.colorRepository.findAll().stream().map(color -> new ColorOption(
                color.getId(),
                color.getCode(),
                color.getName()
        )).toList();
    }
}
