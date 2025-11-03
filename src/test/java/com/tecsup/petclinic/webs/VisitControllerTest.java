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

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VisitControllerTest {

    @Autowired
    private ObjectMapper om;


    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testCreateVisit() throws Exception {

        VisitDTO newVisit = new VisitDTO();
        newVisit.setPetId(1L);
        newVisit.setVisitDate(LocalDate.of(2025, 11, 3));
        newVisit.setDescription("Control general");

        this.mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.petId", is(1)))
                .andExpect(jsonPath("$.description", is("Control general")));
    }
    @Test
    public void testFindVisitOK() throws Exception {
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setPetId(2L);
        visitDTO.setVisitDate(LocalDate.of(2025, 11, 3));
        visitDTO.setDescription("Vacunación");

        ResultActions mvcActions = mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(visitDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");
        this.mockMvc.perform(get("/visits/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.description", is("Vacunación")));
    }
    @Test
    public void testFindVisitKO() throws Exception {
        this.mockMvc.perform(get("/visits/99999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    public void testUpdateVisit() throws Exception {
        VisitDTO newVisit = new VisitDTO();
        newVisit.setPetId(3L);
        newVisit.setVisitDate(LocalDate.of(2025, 11, 3));
        newVisit.setDescription("visita ini");

        ResultActions mvcActions = mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");
        VisitDTO updatedVisit = new VisitDTO();
        updatedVisit.setId(Long.valueOf(id));
        updatedVisit.setPetId(3L);
        updatedVisit.setVisitDate(LocalDate.of(2025, 11, 3));
        updatedVisit.setDescription("Chequeo de control");

        mockMvc.perform(put("/visits/" + id)
                        .content(om.writeValueAsString(updatedVisit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Chequeo de control")));
    }
    @Test
    public void testDeleteVisit() throws Exception {
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setPetId(4L);
        visitDTO.setVisitDate(LocalDate.of(2025, 11, 3));
        visitDTO.setDescription("Desparasitación");

        ResultActions mvcActions = mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(visitDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");
        mockMvc.perform(delete("/visits/" + id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/visits/" + id))
                .andExpect(status().isNotFound());
    }
}
