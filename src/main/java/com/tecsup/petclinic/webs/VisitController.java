package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.mapper.VisitMapper;
import com.tecsup.petclinic.services.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class VisitController {

	private final VisitService visitService;
	private final VisitMapper mapper;

	public VisitController(VisitService visitService, VisitMapper mapper) {
		this.visitService = visitService;
		this.mapper = mapper;
	}

	@GetMapping("/visits")
	public ResponseEntity<List<VisitDTO>> findAll() {
		List<VisitDTO> visits = visitService.findAll();
		return ResponseEntity.ok(visits);
	}

	@PostMapping("/visits")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<VisitDTO> create(@RequestBody VisitDTO visitDTO) {
		VisitDTO newVisit = visitService.create(visitDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(newVisit);
	}

	@GetMapping("/visits/{id}")
	public ResponseEntity<VisitDTO> findById(@PathVariable Integer id) {
		try {
			VisitDTO dto = visitService.findById(id);
			return ResponseEntity.ok(dto);
		} catch (VisitNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/visits/{id}")
	public ResponseEntity<VisitDTO> update(@RequestBody VisitDTO visitDTO, @PathVariable Integer id) {
		try {
			VisitDTO existing = visitService.findById(id);

			existing.setPetId(visitDTO.getPetId());
			existing.setVisitDate(visitDTO.getVisitDate());
			existing.setDescription(visitDTO.getDescription());

			visitService.update(existing);
			return ResponseEntity.ok(existing);
		} catch (VisitNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/visits/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		try {
			visitService.delete(id);
			return ResponseEntity.ok("Delete ID :" + id);
		} catch (VisitNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
