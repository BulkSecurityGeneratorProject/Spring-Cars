package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Gas;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Gas entity.
 */
@SuppressWarnings("unused")
public interface GasRepository extends JpaRepository<Gas,Long> {

}
