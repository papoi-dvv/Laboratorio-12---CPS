package com.tecsup.petclinic.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VisitDTO {
    private Long id;
    private Long petId;
    private LocalDate visitDate;
    private String description;

    public VisitDTO(Long id, Integer id1, LocalDate visitDate, String description) {
    }
}
