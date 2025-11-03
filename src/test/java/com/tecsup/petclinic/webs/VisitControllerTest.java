package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VisitControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VisitRepository visitRepo;

    @Autowired
    private PetRepository petRepo;

    @Test
    public void testCreateVisit() throws Exception {

        Date birthDate = Date.from(LocalDate.of(2020, 5, 15).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Pet pet = new Pet();
        pet.setName("Rocky");
        pet.setTypeId(1);
        pet.setOwnerId(1);
        pet.setBirthDate(birthDate);
        pet = petRepo.save(pet);

        Visit visit = new Visit();
        visit.setPet(pet);
        visit.setVisitDate(LocalDate.now());
        visit.setDescription("Revisión general");

        mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(visit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is("Revisión general")));
    }

    @Test
    public void testFindVisitById() throws Exception {

        Date birthDate = Date.from(LocalDate.of(2021, 3, 10)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        Pet pet = new Pet();
        pet.setName("Luna");
        pet.setTypeId(1);
        pet.setOwnerId(1);
        pet.setBirthDate(birthDate);
        pet = petRepo.save(pet);

        Visit visit = new Visit();
        visit.setPet(pet);
        visit.setVisitDate(LocalDate.now());
        visit.setDescription("Chequeo de vacunas");
        visit = visitRepo.save(visit);

        mockMvc.perform(get("/visits/" + visit.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Chequeo de vacunas")));
    }

    @Test
    public void testDeleteVisit() throws Exception {

        Date birthDate = Date.from(LocalDate.of(2019, 9, 5)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        Pet pet = new Pet();
        pet.setName("Max");
        pet.setTypeId(1);
        pet.setOwnerId(1);
        pet.setBirthDate(birthDate);
        pet = petRepo.save(pet);

        Visit visit = new Visit();
        visit.setPet(pet);
        visit.setVisitDate(LocalDate.now());
        visit.setDescription("Control dental");
        visit = visitRepo.save(visit);

        mockMvc.perform(delete("/visits/" + visit.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteVisitKO() throws Exception {
        mockMvc.perform(delete("/visits/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}