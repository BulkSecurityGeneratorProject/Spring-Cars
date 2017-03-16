package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Manufacturer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Manufacturer entity.
 */
@SuppressWarnings("unused")
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long> {

}
