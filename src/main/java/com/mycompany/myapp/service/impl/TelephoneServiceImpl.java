package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.TelephoneService;
import com.mycompany.myapp.domain.Telephone;
import com.mycompany.myapp.repository.TelephoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Telephone.
 */
@Service
@Transactional
public class TelephoneServiceImpl implements TelephoneService {

    private final Logger log = LoggerFactory.getLogger(TelephoneServiceImpl.class);

    private final TelephoneRepository telephoneRepository;

    public TelephoneServiceImpl(TelephoneRepository telephoneRepository) {
        this.telephoneRepository = telephoneRepository;
    }

    /**
     * Save a telephone.
     *
     * @param telephone the entity to save
     * @return the persisted entity
     */
    @Override
    public Telephone save(Telephone telephone) {
        log.debug("Request to save Telephone : {}", telephone);
        return telephoneRepository.save(telephone);
    }

    /**
     * Get all the telephones.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Telephone> findAll() {
        log.debug("Request to get all Telephones");
        return telephoneRepository.findAll();
    }

    /**
     * Get one telephone by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Telephone findOne(Long id) {
        log.debug("Request to get Telephone : {}", id);
        return telephoneRepository.findOne(id);
    }

    /**
     * Delete the telephone by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Telephone : {}", id);
        telephoneRepository.delete(id);
    }
}
