package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Traction;

import com.mycompany.myapp.repository.TractionRepository;
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
 * REST controller for managing Traction.
 */
@RestController
@RequestMapping("/api")
public class TractionResource {

    private final Logger log = LoggerFactory.getLogger(TractionResource.class);
        
    @Inject
    private TractionRepository tractionRepository;

    /**
     * POST  /tractions : Create a new traction.
     *
     * @param traction the traction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traction, or with status 400 (Bad Request) if the traction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tractions")
    @Timed
    public ResponseEntity<Traction> createTraction(@RequestBody Traction traction) throws URISyntaxException {
        log.debug("REST request to save Traction : {}", traction);
        if (traction.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("traction", "idexists", "A new traction cannot already have an ID")).body(null);
        }
        Traction result = tractionRepository.save(traction);
        return ResponseEntity.created(new URI("/api/tractions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("traction", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tractions : Updates an existing traction.
     *
     * @param traction the traction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traction,
     * or with status 400 (Bad Request) if the traction is not valid,
     * or with status 500 (Internal Server Error) if the traction couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tractions")
    @Timed
    public ResponseEntity<Traction> updateTraction(@RequestBody Traction traction) throws URISyntaxException {
        log.debug("REST request to update Traction : {}", traction);
        if (traction.getId() == null) {
            return createTraction(traction);
        }
        Traction result = tractionRepository.save(traction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("traction", traction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tractions : get all the tractions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tractions in body
     */
    @GetMapping("/tractions")
    @Timed
    public List<Traction> getAllTractions() {
        log.debug("REST request to get all Tractions");
        List<Traction> tractions = tractionRepository.findAll();
        return tractions;
    }

    /**
     * GET  /tractions/:id : get the "id" traction.
     *
     * @param id the id of the traction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traction, or with status 404 (Not Found)
     */
    @GetMapping("/tractions/{id}")
    @Timed
    public ResponseEntity<Traction> getTraction(@PathVariable Long id) {
        log.debug("REST request to get Traction : {}", id);
        Traction traction = tractionRepository.findOne(id);
        return Optional.ofNullable(traction)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tractions/:id : delete the "id" traction.
     *
     * @param id the id of the traction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tractions/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraction(@PathVariable Long id) {
        log.debug("REST request to delete Traction : {}", id);
        tractionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traction", id.toString())).build();
    }

}
