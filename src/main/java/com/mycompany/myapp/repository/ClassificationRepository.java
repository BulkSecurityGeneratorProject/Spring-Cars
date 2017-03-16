package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Classification;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Classification entity.
 */
@SuppressWarnings("unused")
public interface ClassificationRepository extends JpaRepository<Classification,Long> {

}
