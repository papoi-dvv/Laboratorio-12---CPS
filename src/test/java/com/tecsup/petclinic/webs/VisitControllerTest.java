package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.VisitDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test de integración para VisitController
 */
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VisitControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    /**
     * Listar todas las visitas
     */
    @Test
    public void testFindAllVisits() throws Exception {

        this.mockMvc.perform(get("/visits"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").exists());
    }

    /**
     * Buscar una visita existente
     */
    @Test
    public void testFindVisitOK() throws Exception {

        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setPetId(1);
        visitDTO.setVisitDate("2025-11-03");
        visitDTO.setDescription("Chequeo general");

        ResultActions mvcActions = mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(visitDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id", Integer.class);

        mockMvc.perform(get("/visits/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.description", is("Chequeo general")));
    }

    /**
     * Buscar una visita inexistente
     */
    @Test
    public void testFindVisitKO() throws Exception {

        mockMvc.perform(get("/visits/99999"))
                .andExpect(status().isNotFound());
    }

    /**
     * Crear una nueva visita
     */
    @Test
    public void testCreateVisit() throws Exception {

        int PET_ID = 2;
        String VISIT_DATE = "2025-11-03";
        String DESCRIPTION = "Vacunación anual";

        VisitDTO newVisit = new VisitDTO();
        newVisit.setPetId(PET_ID);
        newVisit.setVisitDate(VISIT_DATE);
        newVisit.setDescription(DESCRIPTION);

        this.mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.petId", is(PET_ID)))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)))
                .andExpect(jsonPath("$.visitDate", is(VISIT_DATE)));
    }

    /**
     * Eliminar una visita existente
     */
    @Test
    public void testDeleteVisit() throws Exception {

        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setPetId(3);
        visitDTO.setVisitDate("2025-11-03");
        visitDTO.setDescription("Desparasitación");

        ResultActions mvcActions = mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(visitDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/visits/" + id))
                .andExpect(status().isOk());

        mockMvc.perform(get("/visits/" + id))
                .andExpect(status().isNotFound());
    }

    /**
     * Intentar eliminar una visita inexistente
     */
    @Test
    public void testDeleteVisitKO() throws Exception {

        mockMvc.perform(delete("/visits/99999"))
                .andExpect(status().isNotFound());
    }

    /**
     * Actualizar una visita existente
     */
    @Test
    public void testUpdateVisit() throws Exception {

        VisitDTO newVisit = new VisitDTO();
        newVisit.setPetId(4);
        newVisit.setVisitDate("2025-11-03");
        newVisit.setDescription("Visita inicial");

        ResultActions mvcActions = mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id", Integer.class);

        VisitDTO upVisit = new VisitDTO();
        upVisit.setId(id);
        upVisit.setPetId(4);
        upVisit.setVisitDate("2025-11-03");
        upVisit.setDescription("Chequeo actualizado");

        mockMvc.perform(put("/visits/" + id)
                        .content(om.writeValueAsString(upVisit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Chequeo actualizado")));

        mockMvc.perform(get("/visits/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.description", is("Chequeo actualizado")));

        mockMvc.perform(delete("/visits/" + id))
                .andExpect(status().isOk());
    }
}
