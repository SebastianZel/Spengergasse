package com.example.demo.Presentation.Api;

import com.example.demo.Domain.Person;
import com.example.demo.Persistence.PersonenListRepo;
import com.example.demo.dto.PersonDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PersonenListRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private PersonenListRepo pr;
    private ObjectMapper objectMapper= new ObjectMapper().registerModule(new JavaTimeModule());

    public String asJsonString(final Object obj) {
        try {
            return  objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        Person p = new Person(1L,"patrick","@mail","+43123142523","aufliedadsd","help");
        Person p2 = new Person(2L,"tarik","@mail","+43123142523","aufliedadsd","help");
        Person p3 = new Person(3L,"patrick","@mail","+43123142523","aufliedadsd","help");

        pr.save(p);
        pr.save(p2);
        pr.save(p3);
    }

    @Test
    void getAllPersons() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/api/v1/personenList")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.person").isNotEmpty());

    }

    @Test
    void getPersonbyId() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/api/v1/personenList/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("Methode_TESTGetByID"));
    }

    @Test
    void deletePersonById() throws Exception {
        mvc.perform( MockMvcRequestBuilders.delete("/api/v1/personenList?id=1") )
                .andExpect(status().isOk()).andDo(document("Methode_delete"));;

    }

    @Test
    void createPerson() throws Exception {
        PersonDTO p = new PersonDTO(9L,"Blin","@blinstinkt,", "+43129837", "aufgase","BLINSTINKT");
        mvc.perform( MockMvcRequestBuilders.post("/api/v1/personenList")
                .content(asJsonString(p))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andDo(document("Methode_create"));

    }

    @Test
    void update() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .put("/api/v1/buch/{id}",2)
                .content(asJsonString(new PersonDTO(9L,"Blin","@blinstinkt,", "+43129837", "aufgase","BLINSTINKT")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.titel").value("Java ist doch nicht so schwer"))
                .andDo(document("Methode_update"));

    }
}