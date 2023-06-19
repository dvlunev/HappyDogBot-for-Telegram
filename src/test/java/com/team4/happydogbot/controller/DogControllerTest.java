package com.team4.happydogbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.happydogbot.entity.Dog;
import com.team4.happydogbot.exception.DogNotFoundException;
import com.team4.happydogbot.service.DogService;
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
 * for the class - dog service
 *
 * @see Dog
 * @see DogService
 * @see DogController
 * @see DogControllerTest
 */
@WebMvcTest(DogController.class)
public class DogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private DogService dogService;

    private final Dog expected = new Dog();
    private final Dog expected1 = new Dog();
    private final Dog actual = new Dog();
    private final Dog exceptionDog = new Dog();

    @BeforeEach
    public void setUp() {
        expected.setId(1L);
        expected.setName("Ponchik");
        expected.setBreed("Pudel");
        expected.setYearOfBirth(2020);
        expected.setDescription("Test");

        expected1.setId(2L);
        expected1.setName("Bublik");
        expected1.setBreed("Buldog");
        expected1.setYearOfBirth(2017);
        expected1.setDescription("Test");

        actual.setId(1L);
        actual.setName("Ponchik");
        actual.setBreed("no breed");
        actual.setYearOfBirth(2019);
        actual.setDescription("Test");

        exceptionDog.setId(0L);
        exceptionDog.setName(" ");
        exceptionDog.setBreed(null);
        exceptionDog.setYearOfBirth(0);
        exceptionDog.setDescription("");
    }

    /**
     * Method Testing <b>add()</b> в DogController
     * <br>
     * Mockito: when the method is called <b>DogService::add</b>,
     * returns status 200 and dog <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for getting status 200 and returning a dog when trying to create it and save it to the database")
    void addDogTest200() throws Exception {
        when(dogService.add(expected)).thenReturn(expected);

        mockMvc.perform(
                        post("/dog")
                                .content(objectMapper.writeValueAsString(expected))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    /**
     * Method Testing <b>get()</b> в DogController
     * <br>
     * Mockito: when the method is called <b>DogService::get</b>,
     * returns status 200 and dog <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for status 200 and returning a dog when trying to search for it by id")
    public void getDogTest200() throws Exception {
        Long dogId = expected.getId();

        when(dogService.get(anyLong())).thenReturn(expected);

        mockMvc.perform(
                        get("/dog/{id}", dogId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dogId));
    }

    /**
     * Method Testing <b>get()</b> в DogController
     * <br>
     * Mockito: when the method is called <b>DogService::get</b>,
     * an exception is thrown <b>DogNotFoundException</b> и
     * returns status 404 <b>exceptionDog</b>
     *
     * @throws Exception
     * @throws DogNotFoundException
     */
    @Test
    @DisplayName("Checking for status 404 when trying to search by id of a dog that is not in the database")
    void getDogTest404() throws Exception {
        when(dogService.get(anyLong())).thenThrow(DogNotFoundException.class);

        mockMvc.perform(
                        get("/dog/{id}", exceptionDog.getId().toString()))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>delete()</b> в DogController
     * <br>
     * Mockito: when the method is called <b>DogService::remove</b>,
     * returns status 200 <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for status 200 when trying to remove a dog from the database by id")
    public void deleteDogTest200() throws Exception {
        Long dogId = expected.getId();

        when(dogService.remove(anyLong())).thenReturn(true);

        mockMvc.perform(
                        delete("/dog/{id}", dogId))
                .andExpect(status().isOk());
    }

    /**
     * Method Testing <b>delete()</b> в DogController
     * <br>
     * Mockito: when the method is called <b>DogService::remove</b>,
     * an exception is thrown <b>DogNotFoundException</b> и
     * returns status 404 <b>exceptionDog</b>
     *
     * @throws Exception
     * @throws DogNotFoundException
     */
    @Test
    @DisplayName("Checking for status 404 when trying to delete by id a dog that is not in the database")
    public void deleteDogTest404() throws Exception {
        Long dogId = exceptionDog.getId();

        when(dogService.remove(anyLong())).thenThrow(DogNotFoundException.class);

        mockMvc.perform(
                        delete("/dog/{id}", dogId))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>update()</b> в DogController
     * <br>
     * Mockito: when the method is called <b>DogService::update</b>,
     * returns status 200 and edited dog <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for status 200 when trying to update and save a dog in the database")
    public void updateDogTest200() throws Exception {
        when(dogService.update(expected)).thenReturn(expected);

        mockMvc.perform(
                        put("/dog")
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
     * Method Testing <b>update()</b> в DogController
     * <br>
     * Mockito: when the method is called <b>DogService::update</b>,
     * an exception is thrown <b>DogNotFoundException</b> и
     * returns status 404 <b>exceptionDog</b>
     *
     * @throws Exception
     * @throws DogNotFoundException
     */
    @Test
    @DisplayName("Checking for a 404 status when trying to update and save a dog that is not in the database")
    public void updateDogTest404() throws Exception {
        when(dogService.update(exceptionDog)).thenThrow(DogNotFoundException.class);

        mockMvc.perform(
                        put("/dog")
                                .content(objectMapper.writeValueAsString(exceptionDog))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>getAll()</b> в DogController
     * <br>
     * Mockito: when the method is called <b>DogService::getAll</b>,
     * returns status 200 and dogs collection <b>Arrays.asList(expected, expected1)</b>
     */
    @Test
    @DisplayName("Checking for getting status 200 and returning all dogs when trying to search for them in the database")
    void getAllDogsTest200() throws Exception {
        when(dogService.getAll()).thenReturn(Arrays.asList(expected, expected1));

        mockMvc.perform(
                        get("/dog/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(expected, expected1))));
    }
}
