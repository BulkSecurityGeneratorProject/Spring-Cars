package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Traction;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Traction entity.
 */
@SuppressWarnings("unused")
public interface TractionRepository extends JpaRepository<Traction,Long> {

}
