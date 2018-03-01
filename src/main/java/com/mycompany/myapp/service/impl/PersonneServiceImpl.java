package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PersonneService;
import com.mycompany.myapp.domain.Personne;
import com.mycompany.myapp.repository.PersonneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Personne.
 */
@Service
@Transactional
public class PersonneServiceImpl implements PersonneService {

    private final Logger log = LoggerFactory.getLogger(PersonneServiceImpl.class);

    private final PersonneRepository personneRepository;

    public PersonneServiceImpl(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    /**
     * Save a personne.
     *
     * @param personne the entity to save
     * @return the persisted entity
     */
    @Override
    public Personne save(Personne personne) {
        log.debug("Request to save Personne : {}", personne);
        return personneRepository.save(personne);
    }

    /**
     * Get all the personnes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Personne> findAll(Pageable pageable) {
        log.debug("Request to get all Personnes");
        return personneRepository.findAll(pageable);
    }

    /**
     * Get one personne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Personne findOne(Long id) {
        log.debug("Request to get Personne : {}", id);
        return personneRepository.findOne(id);
    }

    /**
     * Delete the personne by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personne : {}", id);
        personneRepository.delete(id);
    }
}
