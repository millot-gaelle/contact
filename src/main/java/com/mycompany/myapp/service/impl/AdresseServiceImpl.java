package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.AdresseService;
import com.mycompany.myapp.domain.Adresse;
import com.mycompany.myapp.repository.AdresseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Adresse.
 */
@Service
@Transactional
public class AdresseServiceImpl implements AdresseService {

    private final Logger log = LoggerFactory.getLogger(AdresseServiceImpl.class);

    private final AdresseRepository adresseRepository;

    public AdresseServiceImpl(AdresseRepository adresseRepository) {
        this.adresseRepository = adresseRepository;
    }

    /**
     * Save a adresse.
     *
     * @param adresse the entity to save
     * @return the persisted entity
     */
    @Override
    public Adresse save(Adresse adresse) {
        log.debug("Request to save Adresse : {}", adresse);
        return adresseRepository.save(adresse);
    }

    /**
     * Get all the adresses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Adresse> findAll() {
        log.debug("Request to get all Adresses");
        return adresseRepository.findAll();
    }

    /**
     * Get one adresse by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Adresse findOne(Long id) {
        log.debug("Request to get Adresse : {}", id);
        return adresseRepository.findOne(id);
    }

    /**
     * Delete the adresse by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Adresse : {}", id);
        adresseRepository.delete(id);
    }
}
