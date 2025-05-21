package com.nancologist.car360.repository;

import com.nancologist.car360.dto.CarThumbnailDto;
import com.nancologist.car360.model.Car;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CompoundSelection;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

interface CarRepositoryCustom {
    List<CarThumbnailDto> getCarThumbnails();
}

@Repository
class CarRepositoryImpl implements CarRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CarThumbnailDto> getCarThumbnails() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CarThumbnailDto> criteriaQuery = criteriaBuilder.createQuery(CarThumbnailDto.class);
        Root<Car> root = criteriaQuery.from(Car.class);

        CompoundSelection<CarThumbnailDto> carThumbnailDtoCompoundSelection = criteriaBuilder.construct(
                CarThumbnailDto.class,
                root.get("id"),
                root.get("manufacturer"),
                root.get("bodyStyleCode"),
                root.get("model"),
                root.get("color").get("name"),
                root.get("productionDate")
        );

        criteriaQuery.select(carThumbnailDtoCompoundSelection);
        criteriaQuery.orderBy(
                criteriaBuilder.desc(root.get("productionDate"))
        );

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}

public interface CarRepository extends JpaRepository<Car, Long>, CarRepositoryCustom {}
