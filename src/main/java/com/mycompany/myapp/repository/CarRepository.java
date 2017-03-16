package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Car;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Car entity.
 */
@SuppressWarnings("unused")
public interface CarRepository extends JpaRepository<Car,Long> {

    @Query("select distinct car from Car car left join fetch car.classifications left join fetch car.tractions left join fetch car.gases")
    List<Car> findAllWithEagerRelationships();

    @Query("select car from Car car left join fetch car.classifications left join fetch car.tractions left join fetch car.gases where car.id =:id")
    Car findOneWithEagerRelationships(@Param("id") Long id);

}
