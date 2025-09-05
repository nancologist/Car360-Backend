package com.nancologist.car360.repository;

import com.nancologist.car360.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    List<Color> findAllByOrderByName();
}
