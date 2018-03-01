package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Personne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Personne.
 */
public interface PersonneService {

    /**
     * Save a personne.
     *
     * @param personne the entity to save
     * @return the persisted entity
     */
    Personne save(Personne personne);

    /**
     * Get all the personnes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Personne> findAll(Pageable pageable);

    /**
     * Get the "id" personne.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Personne findOne(Long id);

    /**
     * Delete the "id" personne.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
