package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Adresse;
import com.mycompany.myapp.domain.Personne;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Adresse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Long> {

	public List<Adresse> findAllByPersonne(Personne personne);
}
