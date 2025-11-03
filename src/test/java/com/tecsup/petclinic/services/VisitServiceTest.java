package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.tecsup.petclinic.dtos.VisitDTO;
import org.junit.jupiter.api.Test;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.exceptions.VisitNotFoundException;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class VisitServiceTest {

	@Autowired
	private VisitService visitService;

	@Test
	public void testFindVisitById() {

		String DESCRIPTION_EXPECTED = "rabies shot";

		Integer ID = 1;

		VisitDTO visit = null;

		try {
			visit = this.visitService.findById(ID);
		} catch (VisitNotFoundException e) {
			fail(e.getMessage());
		}
		assertEquals(DESCRIPTION_EXPECTED, visit.getDescription());
	}

	/**
	 *
	 */
	@Test
	public void testFindVisitsByPetId() {

		Integer PET_ID = 7;
		int SIZE_EXPECTED = 2;

		List<VisitDTO> visits = this.visitService.findByPetId(PET_ID);

		assertEquals(SIZE_EXPECTED, visits.size());
	}

	/**
	 *
	 */
	@Test
	public void testCreateVisit() {

		String DESCRIPTION = "General checkup";
		Integer PET_ID = 1;
		String VISIT_DATE = "2024-12-10";

		VisitDTO visitDTO = VisitDTO.builder()
				.description(DESCRIPTION)
				.petId(PET_ID)
				.visitDate(VISIT_DATE)
				.build();

		VisitDTO newVisitDTO = this.visitService.create(visitDTO);

		log.info("VISIT CREATED :" + newVisitDTO.toString());

		assertNotNull(newVisitDTO.getId());
		assertEquals(DESCRIPTION, newVisitDTO.getDescription());
		assertEquals(PET_ID, newVisitDTO.getPetId());
		assertEquals(VISIT_DATE, newVisitDTO.getVisitDate());

	}

	/**
	 *
	 */
	@Test
	public void testUpdateVisit() {

		String DESCRIPTION = "Initial checkup";
		Integer PET_ID = 1;
		String VISIT_DATE = "2024-12-10";

		String UP_DESCRIPTION = "Follow-up checkup";
		Integer UP_PET_ID = 2;
		String UP_VISIT_DATE = "2024-12-15";

		VisitDTO visitDTO = VisitDTO.builder()
				.description(DESCRIPTION)
				.petId(PET_ID)
				.visitDate(VISIT_DATE)
				.build();

		// ------------ Create ---------------

		log.info(">" + visitDTO);
		VisitDTO visitDTOCreated = this.visitService.create(visitDTO);
		log.info(">>" + visitDTOCreated);

		// ------------ Update ---------------

		// Prepare data for update
		visitDTOCreated.setDescription(UP_DESCRIPTION);
		visitDTOCreated.setPetId(UP_PET_ID);
		visitDTOCreated.setVisitDate(UP_VISIT_DATE);

		// Execute update
		VisitDTO upgradeVisitDTO = this.visitService.update(visitDTOCreated);
		log.info(">>>>" + upgradeVisitDTO);

		//            EXPECTED        ACTUAL
		assertEquals(UP_DESCRIPTION, upgradeVisitDTO.getDescription());
		assertEquals(UP_PET_ID, upgradeVisitDTO.getPetId());
		assertEquals(UP_VISIT_DATE, upgradeVisitDTO.getVisitDate());
	}

	/**
	 *
	 */
	@Test
	public void testDeleteVisit() {

		String DESCRIPTION = "Test visit for deletion";
		Integer PET_ID = 1;
		String VISIT_DATE = "2024-12-10";

		// ------------ Create ---------------

		VisitDTO visitDTO = VisitDTO.builder()
				.description(DESCRIPTION)
				.petId(PET_ID)
				.visitDate(VISIT_DATE)
				.build();

		VisitDTO newVisitDTO = this.visitService.create(visitDTO);
		log.info("" + visitDTO);

		// ------------ Delete ---------------

		try {
			this.visitService.delete(newVisitDTO.getId());
		} catch (VisitNotFoundException e) {
			fail(e.getMessage());
		}

		// ------------ Validation ---------------

		try {
			this.visitService.findById(newVisitDTO.getId());
			assertTrue(false);
		} catch (VisitNotFoundException e) {
			assertTrue(true);
		}

	}

}