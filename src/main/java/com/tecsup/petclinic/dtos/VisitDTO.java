package com.tecsup.petclinic.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author jgomezm
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VisitDTO {

	private Integer id;
	
	private Integer petId;

	private String visitDate;
	
	private String description;

}
