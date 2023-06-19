package com.team4.happydogbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.happydogbot.entity.Cat;
import com.team4.happydogbot.exception.CatNotFoundException;
import com.team4.happydogbot.service.CatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for checking API endpoints when accessing routes with separate HTTP methods
 * for the class - cat service
 *
 * @see Cat
 * @see CatService
 * @see CatController
 * @see CatControllerTest
 */
@WebMvcTest(CatController.class)
public class CatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CatService catService;

    private final Cat expected = new Cat();
    private final Cat expected1 = new Cat();
    private final Cat actual = new Cat();
    private final Cat exceptionCat = new Cat();

    @BeforeEach
    public void setUp() {
        expected.setId(1L);
        expected.setName("Ponchik");
        expected.setBreed("Bolinez");
        expected.setYearOfBirth(2020);
        expected.setDescription("Test");

        expected1.setId(2L);
        expected1.setName("Bublik");
        expected1.setBreed("Siam");
        expected1.setYearOfBirth(2017);
        expected1.setDescription("Test");

        actual.setId(1L);
        actual.setName("Ponchik");
        actual.setBreed("no breed");
        actual.setYearOfBirth(2020);
        actual.setDescription("Test");

        exceptionCat.setId(0L);
        exceptionCat.setName(" ");
        exceptionCat.setBreed(null);
        exceptionCat.setYearOfBirth(0);
        exceptionCat.setDescription("");
    }

    /**
     * Method Testing <b>add()</b> в CatController
     * <br>
     * Mockito: when the method is called <b>CatService::add</b>,
     * returns status 200 and cat <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for getting status 200 and returning a cat when trying to create it and save it to the database")
    void addCatTest200() throws Exception {
        when(catService.add(expected)).thenReturn(expected);

        mockMvc.perform(
                        post("/cat")
                                .content(objectMapper.writeValueAsString(expected))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    /**
     * Method Testing <b>get()</b> в CatController
     * <br>
     * Mockito: when the method is called <b>CatService::get</b>,
     * returns status 200 and cat <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for status 200 and returning a cat when trying to search for it by id")
    public void getCatTest200() throws Exception {
        Long catId = expected.getId();

        when(catService.get(anyLong())).thenReturn(expected);

        mockMvc.perform(
                        get("/cat/{id}", catId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(catId));
    }

    /**
     * Method Testing <b>get()</b> в CatController
     * <br>
     * Mockito: when the method is called <b>CatService::get</b>,
     * an exception is thrown <b>CatNotFoundException</b> и
     * returns status 404 <b>exceptionCat</b>
     *
     * @throws Exception
     * @throws CatNotFoundException
     */
    @Test
    @DisplayName("Checking for status 404 when trying to search by id of a cat that is not in the database")
    void getCatTest404() throws Exception {
        when(catService.get(anyLong())).thenThrow(CatNotFoundException.class);

        mockMvc.perform(
                        get("/cat/{id}", exceptionCat.getId().toString()))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>delete()</b> в CatController
     * <br>
     * Mockito: when the method is called <b>CatService::remove</b>,
     * returns status 200 <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for status 200 when trying to remove a cat from the database by id")
    public void deleteCatTest200() throws Exception {
        Long catId = expected.getId();

        when(catService.remove(anyLong())).thenReturn(true);

        mockMvc.perform(
                        delete("/cat/{id}", catId))
                .andExpect(status().isOk());
    }

    /**
     * Method Testing <b>delete()</b> в CatController
     * <br>
     * Mockito: when the method is called <b>CatService::remove</b>,
     * an exception is thrown <b>CatNotFoundException</b> и
     * returns status 404 <b>exceptionCat</b>
     *
     * @throws Exception
     * @throws CatNotFoundException
     */
    @Test
    @DisplayName("Checking for status 404 when trying to delete by id a cat that is not in the database")
    public void deleteCatTest404() throws Exception {
        Long catId = exceptionCat.getId();

        when(catService.remove(anyLong())).thenThrow(CatNotFoundException.class);

        mockMvc.perform(
                        delete("/cat/{id}", catId))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>update()</b> в CatController
     * <br>
     * Mockito: when the method is called <b>CatService::update</b>,
     * returns status 200 and an edited cat <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for status 200 when trying to update and save a cat in the database")
    public void updateCatTest200() throws Exception {
        when(catService.update(expected)).thenReturn(expected);

        mockMvc.perform(
                        put("/cat")
                                .content(objectMapper.writeValueAsString(actual))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(actual.getId()))
                .andExpect(jsonPath("$.name").value(actual.getName()))
                .andExpect(jsonPath("$.breed").value(actual.getBreed()))
                .andExpect(jsonPath("$.yearOfBirth").value(actual.getYearOfBirth()))
                .andExpect(jsonPath("$.description").value(actual.getDescription()));
    }

    /**
     * Method Testing <b>update()</b> в CatController
     * <br>
     * Mockito: when the method is called <b>CatService::update</b>,
     * an exception is thrown <b>CatNotFoundException</b> и
     * returns status 404 <b>exceptionCat</b>
     *
     * @throws Exception
     * @throws CatNotFoundException
     */
    @Test
    @DisplayName("Checking for a 404 status when trying to update and save a cat that is not in the database")
    public void updateCatTest404() throws Exception {
        when(catService.update(exceptionCat)).thenThrow(CatNotFoundException.class);

        mockMvc.perform(
                        put("/cat")
                                .content(objectMapper.writeValueAsString(exceptionCat))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>getAll()</b> в CatController
     * <br>
     * Mockito: when the method is called <b>CatService::getAll</b>,
     * returns status 200 and cats collection <b>Arrays.asList(expected, expected1)</b>
     */
    @Test
    @DisplayName("Checking for getting status 200 and returning all cats when trying to search for them in the database")
    void getAllCatsTest200() throws Exception {
        when(catService.getAll()).thenReturn(Arrays.asList(expected, expected1));

        mockMvc.perform(
                        get("/cat/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(expected, expected1))));
    }
}
