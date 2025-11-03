package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.repositories.VisitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

    private final VisitRepository visitRepository;

    public VisitController(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @PostMapping
    public ResponseEntity<Visit> create(@RequestBody Visit visit) {
        Visit savedVisit = visitRepository.save(visit);
        return new ResponseEntity<>(savedVisit, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visit> findById(@PathVariable Long id) {
        Optional<Visit> visit = visitRepository.findById(id);
        return visit.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

