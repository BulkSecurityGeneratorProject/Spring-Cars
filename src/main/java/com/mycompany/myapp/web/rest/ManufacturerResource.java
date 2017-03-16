package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Manufacturer;

import com.mycompany.myapp.repository.ManufacturerRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Manufacturer.
 */
@RestController
@RequestMapping("/api")
public class ManufacturerResource {

    private final Logger log = LoggerFactory.getLogger(ManufacturerResource.class);
        
    @Inject
    private ManufacturerRepository manufacturerRepository;

    /**
     * POST  /manufacturers : Create a new manufacturer.
     *
     * @param manufacturer the manufacturer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new manufacturer, or with status 400 (Bad Request) if the manufacturer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/manufacturers")
    @Timed
    public ResponseEntity<Manufacturer> createManufacturer(@RequestBody Manufacturer manufacturer) throws URISyntaxException {
        log.debug("REST request to save Manufacturer : {}", manufacturer);
        if (manufacturer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("manufacturer", "idexists", "A new manufacturer cannot already have an ID")).body(null);
        }
        Manufacturer result = manufacturerRepository.save(manufacturer);
        return ResponseEntity.created(new URI("/api/manufacturers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("manufacturer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /manufacturers : Updates an existing manufacturer.
     *
     * @param manufacturer the manufacturer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated manufacturer,
     * or with status 400 (Bad Request) if the manufacturer is not valid,
     * or with status 500 (Internal Server Error) if the manufacturer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/manufacturers")
    @Timed
    public ResponseEntity<Manufacturer> updateManufacturer(@RequestBody Manufacturer manufacturer) throws URISyntaxException {
        log.debug("REST request to update Manufacturer : {}", manufacturer);
        if (manufacturer.getId() == null) {
            return createManufacturer(manufacturer);
        }
        Manufacturer result = manufacturerRepository.save(manufacturer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("manufacturer", manufacturer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /manufacturers : get all the manufacturers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of manufacturers in body
     */
    @GetMapping("/manufacturers")
    @Timed
    public List<Manufacturer> getAllManufacturers() {
        log.debug("REST request to get all Manufacturers");
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        return manufacturers;
    }

    /**
     * GET  /manufacturers/:id : get the "id" manufacturer.
     *
     * @param id the id of the manufacturer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the manufacturer, or with status 404 (Not Found)
     */
    @GetMapping("/manufacturers/{id}")
    @Timed
    public ResponseEntity<Manufacturer> getManufacturer(@PathVariable Long id) {
        log.debug("REST request to get Manufacturer : {}", id);
        Manufacturer manufacturer = manufacturerRepository.findOne(id);
        return Optional.ofNullable(manufacturer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /manufacturers/:id : delete the "id" manufacturer.
     *
     * @param id the id of the manufacturer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/manufacturers/{id}")
    @Timed
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long id) {
        log.debug("REST request to delete Manufacturer : {}", id);
        manufacturerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("manufacturer", id.toString())).build();
    }

}
