package com.tecsup.petclinic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecsup.petclinic.entities.Visit;

/**
 * 
 * @author jgomezm
 *
 */
@Repository
public interface VisitRepository 
	extends JpaRepository<Visit, Integer> {

	// Fetch visits by petId
	List<Visit> findByPetId(Integer petId);

	// Fetch visits by Id

	@Override
	List<Visit> findAll();


}
