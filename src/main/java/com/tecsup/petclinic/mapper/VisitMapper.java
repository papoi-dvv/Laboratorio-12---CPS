package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * 
 * @author jgomezm
 *
 */
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface VisitMapper {

	VisitMapper INSTANCE = Mappers.getMapper(VisitMapper.class);

	@Mapping(source = "pet.id", target = "petId")
	@Mapping(source = "visitDate", target = "visitDate", qualifiedByName = "localDateToString")
	VisitDTO mapToDto(Visit visit);

	@Mapping(source = "visitDate", target = "visitDate", qualifiedByName = "stringToLocalDate")
	@Mapping(source = "petId", target = "pet", ignore = true)
	Visit mapToEntity(VisitDTO visitDTO);

	@org.mapstruct.Named("stringToLocalDate")
	default LocalDate stringToLocalDate(String dateStr) {
		if (dateStr == null || dateStr.isEmpty()) {
			return null;
		}

		LocalDate date = null;
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			date = LocalDate.parse(dateStr, dateFormat);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	@org.mapstruct.Named("localDateToString")
	default String localDateToString(LocalDate date) {
		if (date != null) {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return dateFormat.format(date);
		} else {
			return "";
		}
	}

	List<VisitDTO> mapToDtoList(List<Visit> visitList);

	List<Visit> mapToEntityList(List<VisitDTO> visitDTOList);

}
