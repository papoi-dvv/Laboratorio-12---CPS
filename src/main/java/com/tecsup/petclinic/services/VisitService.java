package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;

import java.util.List;

/**
 * Servicio para gestionar las visitas de las mascotas.
 *
 * Similar a PetService, define las operaciones CRUD y de búsqueda.
 *
 * @author tu_nombre
 */
public interface VisitService {

    /**
     * Crea una nueva visita.
     *
     * @param visitDTO DTO con los datos de la visita.
     * @return La visita creada.
     */
    VisitDTO create(VisitDTO visitDTO);

    /**
     * Actualiza una visita existente.
     *
     * @param visitDTO DTO con los nuevos datos.
     * @return La visita actualizada.
     * @throws VisitNotFoundException si no existe la visita.
     */
    VisitDTO update(VisitDTO visitDTO) throws VisitNotFoundException;

    /**
     * Elimina una visita por su ID.
     *
     * @param id identificador de la visita.
     * @throws VisitNotFoundException si no existe la visita.
     */
    void delete(Integer id) throws VisitNotFoundException;

    /**
     * Busca una visita por su ID.
     *
     * @param id identificador de la visita.
     * @return DTO con los datos de la visita.
     * @throws VisitNotFoundException si no existe la visita.
     */
    VisitDTO findById(Integer id) throws VisitNotFoundException;

    /**
     * Lista todas las visitas registradas.
     *
     * @return lista de visitas.
     */
    List<Visit> findAll();

    /**
     * Busca todas las visitas asociadas a un pet_id.
     *
     * @param petId identificador de la mascota.
     * @return lista de visitas del pet.
     */
    List<Visit> findByPetId(Integer petId);
}
