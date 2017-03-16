package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Gas;

import com.mycompany.myapp.repository.GasRepository;
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
 * REST controller for managing Gas.
 */
@RestController
@RequestMapping("/api")
public class GasResource {

    private final Logger log = LoggerFactory.getLogger(GasResource.class);
        
    @Inject
    private GasRepository gasRepository;

    /**
     * POST  /gases : Create a new gas.
     *
     * @param gas the gas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gas, or with status 400 (Bad Request) if the gas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gases")
    @Timed
    public ResponseEntity<Gas> createGas(@RequestBody Gas gas) throws URISyntaxException {
        log.debug("REST request to save Gas : {}", gas);
        if (gas.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("gas", "idexists", "A new gas cannot already have an ID")).body(null);
        }
        Gas result = gasRepository.save(gas);
        return ResponseEntity.created(new URI("/api/gases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("gas", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gases : Updates an existing gas.
     *
     * @param gas the gas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gas,
     * or with status 400 (Bad Request) if the gas is not valid,
     * or with status 500 (Internal Server Error) if the gas couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gases")
    @Timed
    public ResponseEntity<Gas> updateGas(@RequestBody Gas gas) throws URISyntaxException {
        log.debug("REST request to update Gas : {}", gas);
        if (gas.getId() == null) {
            return createGas(gas);
        }
        Gas result = gasRepository.save(gas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("gas", gas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gases : get all the gases.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gases in body
     */
    @GetMapping("/gases")
    @Timed
    public List<Gas> getAllGases() {
        log.debug("REST request to get all Gases");
        List<Gas> gases = gasRepository.findAll();
        return gases;
    }

    /**
     * GET  /gases/:id : get the "id" gas.
     *
     * @param id the id of the gas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gas, or with status 404 (Not Found)
     */
    @GetMapping("/gases/{id}")
    @Timed
    public ResponseEntity<Gas> getGas(@PathVariable Long id) {
        log.debug("REST request to get Gas : {}", id);
        Gas gas = gasRepository.findOne(id);
        return Optional.ofNullable(gas)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /gases/:id : delete the "id" gas.
     *
     * @param id the id of the gas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gases/{id}")
    @Timed
    public ResponseEntity<Void> deleteGas(@PathVariable Long id) {
        log.debug("REST request to delete Gas : {}", id);
        gasRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("gas", id.toString())).build();
    }

}
