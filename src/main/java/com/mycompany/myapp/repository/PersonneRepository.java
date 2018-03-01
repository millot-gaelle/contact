package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Personne;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Personne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {

}
