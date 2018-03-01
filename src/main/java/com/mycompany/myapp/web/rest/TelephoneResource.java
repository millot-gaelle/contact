package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Telephone;
import com.mycompany.myapp.service.TelephoneService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Telephone.
 */
@RestController
@RequestMapping("/api")
public class TelephoneResource {

    private final Logger log = LoggerFactory.getLogger(TelephoneResource.class);

    private static final String ENTITY_NAME = "telephone";

    private final TelephoneService telephoneService;

    public TelephoneResource(TelephoneService telephoneService) {
        this.telephoneService = telephoneService;
    }

    /**
     * POST  /telephones : Create a new telephone.
     *
     * @param telephone the telephone to create
     * @return the ResponseEntity with status 201 (Created) and with body the new telephone, or with status 400 (Bad Request) if the telephone has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/telephones")
    @Timed
    public ResponseEntity<Telephone> createTelephone(@Valid @RequestBody Telephone telephone) throws URISyntaxException {
        log.debug("REST request to save Telephone : {}", telephone);
        if (telephone.getId() != null) {
            throw new BadRequestAlertException("A new telephone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Telephone result = telephoneService.save(telephone);
        return ResponseEntity.created(new URI("/api/telephones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /telephones : Updates an existing telephone.
     *
     * @param telephone the telephone to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated telephone,
     * or with status 400 (Bad Request) if the telephone is not valid,
     * or with status 500 (Internal Server Error) if the telephone couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/telephones")
    @Timed
    public ResponseEntity<Telephone> updateTelephone(@Valid @RequestBody Telephone telephone) throws URISyntaxException {
        log.debug("REST request to update Telephone : {}", telephone);
        if (telephone.getId() == null) {
            return createTelephone(telephone);
        }
        Telephone result = telephoneService.save(telephone);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, telephone.getId().toString()))
            .body(result);
    }

    /**
     * GET  /telephones : get all the telephones.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of telephones in body
     */
    @GetMapping("/telephones")
    @Timed
    public List<Telephone> getAllTelephones() {
        log.debug("REST request to get all Telephones");
        return telephoneService.findAll();
        }

    /**
     * GET  /telephones/:id : get the "id" telephone.
     *
     * @param id the id of the telephone to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the telephone, or with status 404 (Not Found)
     */
    @GetMapping("/telephones/{id}")
    @Timed
    public ResponseEntity<Telephone> getTelephone(@PathVariable Long id) {
        log.debug("REST request to get Telephone : {}", id);
        Telephone telephone = telephoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(telephone));
    }

    /**
     * DELETE  /telephones/:id : delete the "id" telephone.
     *
     * @param id the id of the telephone to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/telephones/{id}")
    @Timed
    public ResponseEntity<Void> deleteTelephone(@PathVariable Long id) {
        log.debug("REST request to delete Telephone : {}", id);
        telephoneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
