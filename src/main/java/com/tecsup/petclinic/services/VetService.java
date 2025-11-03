package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;

import java.util.List;

public interface VetService {
    /**
     * @param vetDTO
     * @return
     */
    VetDTO create(VetDTO vetDTO);

    /**
     *
     * @param vet
     * @return
     */
    VetDTO update(VetDTO vet);

    /**
     *
     * @param id
     * @throws VetNotFoundException
     */
    void delete(Integer id) throws VetNotFoundException;

    /**
     *
     * @param id
     * @return
     */
    VetDTO findById(Integer id) throws VetNotFoundException;

    /**
     *
     * @return
     */
    List<VetDTO> findAll();

    /**
     *
     * @param firstName
     * @return
     */
    List<VetDTO> findByFirstName(String firstName);

    /**
     *
     * @param lastName
     * @return
     */
    List<VetDTO> findByLastName(String lastName);
}
