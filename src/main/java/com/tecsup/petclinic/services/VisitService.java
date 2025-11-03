package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;

import java.util.List;

/**
 * 
 * @author jgomezm
 *
 */
public interface VisitService {

	/**
	 * 
	 * @param visitDTO
	 * @return
	 */
	public VisitDTO create(VisitDTO visitDTO);

	/**
	 * 
	 * @param visit
	 * @return
	 */
	VisitDTO update(VisitDTO visit);

	/**
	 * 
	 * @param id
	 * @throws VisitNotFoundException
	 */
	void delete(Integer id) throws VisitNotFoundException;

	/**
	 * 
	 * @param id
	 * @return
	 */
	VisitDTO findById(Integer id) throws VisitNotFoundException;

	/**
	 * 
	 * @param petId
	 * @return
	 */
	List<VisitDTO> findByPetId(Integer petId);

	/**
	 *
	 * @return
	 */
	List<VisitDTO> findAll();
}
