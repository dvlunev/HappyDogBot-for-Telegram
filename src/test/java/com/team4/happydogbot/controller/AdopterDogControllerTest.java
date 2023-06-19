package com.team4.happydogbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.happydogbot.entity.AdopterDog;
import com.team4.happydogbot.entity.Status;
import com.team4.happydogbot.exception.AdopterDogNotFoundException;
import com.team4.happydogbot.service.AdopterDogService;
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
 * for the class - service of adoptive parents of dogs
 *
 * @see AdopterDog
 * @see AdopterDogService
 * @see AdopterDogController
 * @see AdopterDogControllerTest
 */
@WebMvcTest(AdopterDogController.class)
public class AdopterDogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AdopterDogService adopterDogService;

    private final AdopterDog expected = new AdopterDog();
    private final AdopterDog expected1 = new AdopterDog();
    private final AdopterDog actual = new AdopterDog();
    private final AdopterDog exceptionAdopterDog = new AdopterDog();

    @BeforeEach
    public void setUp() {
        expected.setChatId(1234567890L);
        expected.setFirstName("Ivan");
        expected.setLastName("Ivanov");
        expected.setUserName("iiivanov");
        expected.setAge(33);
        expected.setAddress("МСК...");
        expected.setTelephoneNumber("7951...");
        expected.setState(Status.REGISTRATION);

        expected1.setChatId(9876543210L);
        expected1.setFirstName("Petr");
        expected1.setLastName("Petrov");
        expected1.setUserName("pppetrov");
        expected1.setAge(23);
        expected1.setAddress("МСК...");
        expected1.setTelephoneNumber("7902...");
        expected1.setState(Status.REGISTRATION);

        actual.setChatId(1234567890L);
        actual.setFirstName("Ivan");
        actual.setLastName("Ivanov");
        actual.setUserName("iiivanov");
        actual.setAge(33);
        actual.setAddress("КРАСНОДАР...");
        actual.setTelephoneNumber("7964...");
        actual.setState(Status.PROBATION);

        exceptionAdopterDog.setChatId(0L);
        exceptionAdopterDog.setFirstName(" ");
        exceptionAdopterDog.setLastName(null);
        exceptionAdopterDog.setUserName(null);
        exceptionAdopterDog.setAge(0);
        exceptionAdopterDog.setAddress("");
        exceptionAdopterDog.setTelephoneNumber(null);
        exceptionAdopterDog.setState(null);
    }

    /**
     * Method Testing <b>add()</b> в AdopterDogController
     * <br>
     * Mockito: when the method is called <b>AdopterDogService::add</b>,
     * return status 200 and dog adopter <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for Status 200 and Return of the Dog's Adopter " +
            "when trying to create it and store it in the database")
    void addAdopterDogTest200() throws Exception {
        when(adopterDogService.add(expected)).thenReturn(expected);

        mockMvc.perform(
                        post("/adopter_dog")
                                .content(objectMapper.writeValueAsString(expected))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    /**
     * Method Testing <b>get()</b> в AdopterDogController
     * <br>
     * Mockito: when the method is called <b>AdopterDogService::get</b>,
     * return status 200 and dog adopter <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for getting status 200 and returning the dog's adopter when trying to search for him by chatId")
    public void getAdopterDogTest200() throws Exception {
        Long chatId = expected.getChatId();

        when(adopterDogService.get(anyLong())).thenReturn(expected);

        mockMvc.perform(
                        get("/adopter_dog/{chatId}", chatId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(chatId));
    }

    /**
     * Method Testing <b>get()</b> в AdopterDogController
     * <br>
     * Mockito: when the method is called <b>AdopterDogService::get</b>,
     * an exception is thrown <b>AdopterDogNotFoundException</b> и
     * return status 404 <b>exceptionAdopterDog</b>
     *
     * @throws Exception
     * @throws AdopterDogNotFoundException
     */
    @Test
    @DisplayName("Checking for status 404 when trying to search by chatId " +
            "dog's adopter, which is not in the database")
    void getAdopterDogTest404() throws Exception {
        when(adopterDogService.get(anyLong())).thenThrow(AdopterDogNotFoundException.class);

        mockMvc.perform(
                        get("/adopter_dog/{chatId}", exceptionAdopterDog.getChatId().toString()))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>delete()</b> в AdopterDogController
     * <br>
     * Mockito: when the method is called <b>AdopterDogService::remove</b>,
     * return status 200 <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for status 200 when trying to remove a dog's adopter from the database by chatId")
    public void deleteAdopterDogTest200() throws Exception {
        Long chatId = expected.getChatId();

        when(adopterDogService.remove(anyLong())).thenReturn(true);

        mockMvc.perform(
                        delete("/adopter_dog/{chatId}", chatId))
                .andExpect(status().isOk());
    }

    /**
     * Method Testing <b>delete()</b> в AdopterDogController
     * <br>
     * Mockito: when the method is called <b>AdopterDogService::remove</b>,
     * an exception is thrown <b>AdopterDogNotFoundException</b> и
     * return status 404 <b>exceptionAdopterDog</b>
     *
     * @throws Exception
     * @throws AdopterDogNotFoundException
     */
    @Test
    @DisplayName("Checking for status 404 when trying to delete by chatId " +
            "dog's adopter, which is not in the database ")
    public void deleteAdopterDogTest404() throws Exception {
        Long chatId = exceptionAdopterDog.getChatId();

        when(adopterDogService.remove(anyLong())).thenThrow(AdopterDogNotFoundException.class);

        mockMvc.perform(
                        delete("/adopter_dog/{chatId}", chatId))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>update()</b> в AdopterDogController
     * <br>
     * Mockito: when the method is called <b>AdopterDogService::update</b>,
     * returns status 200 and edited dog adopter <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for status 200 when trying to update and save dog adopter in database")
    public void updateAdopterDogTest200() throws Exception {
        when(adopterDogService.update(expected)).thenReturn(expected);

        mockMvc.perform(
                        put("/adopter_dog")
                                .content(objectMapper.writeValueAsString(actual))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(actual.getChatId()))
                .andExpect(jsonPath("$.firstName").value(actual.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(actual.getLastName()))
                .andExpect(jsonPath("$.userName").value(actual.getUserName()))
                .andExpect(jsonPath("$.age").value(actual.getAge()))
                .andExpect(jsonPath("$.address").value(actual.getAddress()))
                .andExpect(jsonPath("$.telephoneNumber").value(actual.getTelephoneNumber()))
                .andExpect(jsonPath("$.state").value(actual.getState().name()));
    }

    /**
     * Method Testing <b>update()</b> в AdopterDogController
     * <br>
     * Mockito: when the method is called <b>AdopterDogService::update</b>,
     * an exception is thrown <b>AdopterDogNotFoundException</b> и
     * return status 404 <b>exceptionAdopterDog</b>
     *
     * @throws Exception
     * @throws AdopterDogNotFoundException
     */
    @Test
    @DisplayName("Checking for a 404 status when trying to update and save " +
            "dog's adopter, which is not in the database")
    public void updateAdopterDogTest404() throws Exception {
        when(adopterDogService.update(exceptionAdopterDog)).thenThrow(AdopterDogNotFoundException.class);

        mockMvc.perform(
                        put("/adopter_dog")
                                .content(objectMapper.writeValueAsString(exceptionAdopterDog))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>getAll()</b> в AdopterDogController
     * <br>
     * Mockito: when the method is called <b>AdopterDogService::getAll</b>,
     * return status 200 and collection of dog adopters <b>Arrays.asList(expected, expected1)</b>
     */
    @Test
    @DisplayName("Checking for Status 200 Receipt and Return of All Dog Adopters " +
            "when trying to find them in the database")
    void getAllAdopterDogsTest200() throws Exception {
        when(adopterDogService.getAll()).thenReturn(Arrays.asList(expected, expected1));

        mockMvc.perform(
                        get("/adopter_dog/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(expected, expected1))));
    }

    @Test
    @DisplayName("Checking for status 200 when sending a message to a user")
    public void sendMessageWithValidChatId() throws Exception {
        Long chatId = expected.getChatId();
        String textToSend = "Hello, world!";

        when(adopterDogService.get(chatId)).thenReturn(expected);

        mockMvc.perform(get("/adopter_dog/send_message")
                        .param("chatId", String.valueOf(chatId))
                        .param("textToSend", textToSend))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Checking for a 404 status when sending a message to a user that doesn't exist")
    public void sendMessageWithInvalidChatId() throws Exception {
        Long chatId = anyLong();
        String textToSend = "Hello, world!";
        when(adopterDogService.get(chatId)).thenReturn(null);

        mockMvc.perform(get("/adopter_dog/send_message")
                        .param("chatId", String.valueOf(chatId))
                        .param("textToSend", textToSend))
                .andExpect(status().isNotFound());
    }
}
