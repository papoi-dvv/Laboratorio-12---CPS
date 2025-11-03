package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.tecsup.petclinic.dtos.VetDTO;
import org.junit.jupiter.api.Test;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.exceptions.VetNotFoundException;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class VetServiceTest {

	@Autowired
	private VetService vetService;

	@Test
	public void testFindVetById() {

		String FIRST_NAME_EXPECTED = "James";

		Integer ID = 1;

		VetDTO vet = null;

		try {
			vet = this.vetService.findById(ID);
		} catch (VetNotFoundException e) {
			fail(e.getMessage());
		}
		assertEquals(FIRST_NAME_EXPECTED, vet.getFirstName());
	}

	/**
	 *
	 */
	@Test
	public void testFindVetByFirstName() {

		String FIND_FIRST_NAME = "James";
		int SIZE_EXPECTED = 1;

		List<VetDTO> vets = this.vetService.findByFirstName(FIND_FIRST_NAME);

		assertEquals(SIZE_EXPECTED, vets.size());
	}

	/**
	 *
	 */
	@Test
	public void testFindVetByLastName() {

		String FIND_LAST_NAME = "Carter";
		int SIZE_EXPECTED = 1;

		List<VetDTO> vets = this.vetService.findByLastName(FIND_LAST_NAME);

		assertEquals(SIZE_EXPECTED, vets.size());
	}

	/**
	 *
	 */
	@Test
	public void testCreateVet() {

		String FIRST_NAME = "John";
		String LAST_NAME = "Doe";

		VetDTO vetDTO = VetDTO.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.build();

		VetDTO newVetDTO = this.vetService.create(vetDTO);

		log.info("VET CREATED :" + newVetDTO.toString());

		assertNotNull(newVetDTO.getId());
		assertEquals(FIRST_NAME, newVetDTO.getFirstName());
		assertEquals(LAST_NAME, newVetDTO.getLastName());

	}

	/**
	 *
	 */
	@Test
	public void testUpdateVet() {

		String FIRST_NAME = "Jane";
		String LAST_NAME = "Smith";

		String UP_FIRST_NAME = "Jane";
		String UP_LAST_NAME = "Johnson";

		VetDTO vetDTO = VetDTO.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.build();

		// ------------ Create ---------------

		log.info(">" + vetDTO);
		VetDTO vetDTOCreated = this.vetService.create(vetDTO);
		log.info(">>" + vetDTOCreated);

		// ------------ Update ---------------

		// Prepare data for update
		vetDTOCreated.setFirstName(UP_FIRST_NAME);
		vetDTOCreated.setLastName(UP_LAST_NAME);

		// Execute update
		VetDTO upgradeVetDTO = this.vetService.update(vetDTOCreated);
		log.info(">>>>" + upgradeVetDTO);

		//            EXPECTED        ACTUAL
		assertEquals(UP_FIRST_NAME, upgradeVetDTO.getFirstName());
		assertEquals(UP_LAST_NAME, upgradeVetDTO.getLastName());
	}

	/**
	 *
	 */
	@Test
	public void testDeleteVet() {

		String FIRST_NAME = "Test";
		String LAST_NAME = "Vet";

		// ------------ Create ---------------

		VetDTO vetDTO = VetDTO.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.build();

		VetDTO newVetDTO = this.vetService.create(vetDTO);
		log.info("" + vetDTO);

		// ------------ Delete ---------------

		try {
			this.vetService.delete(newVetDTO.getId());
		} catch (VetNotFoundException e) {
			fail(e.getMessage());
		}

		// ------------ Validation ---------------

		try {
			this.vetService.findById(newVetDTO.getId());
			assertTrue(false);
		} catch (VetNotFoundException e) {
			assertTrue(true);
		}

	}

}
