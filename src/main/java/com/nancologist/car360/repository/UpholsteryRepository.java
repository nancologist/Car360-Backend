package com.nancologist.car360.repository;

import com.nancologist.car360.model.Upholstery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpholsteryRepository extends JpaRepository<Upholstery, Long> {
}
