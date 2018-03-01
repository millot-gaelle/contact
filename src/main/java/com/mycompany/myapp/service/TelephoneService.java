package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Telephone;
import java.util.List;

/**
 * Service Interface for managing Telephone.
 */
public interface TelephoneService {

    /**
     * Save a telephone.
     *
     * @param telephone the entity to save
     * @return the persisted entity
     */
    Telephone save(Telephone telephone);

    /**
     * Get all the telephones.
     *
     * @return the list of entities
     */
    List<Telephone> findAll();

    /**
     * Get the "id" telephone.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Telephone findOne(Long id);

    /**
     * Delete the "id" telephone.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
