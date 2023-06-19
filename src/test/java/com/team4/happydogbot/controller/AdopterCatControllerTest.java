package com.team4.happydogbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.happydogbot.entity.AdopterCat;
import com.team4.happydogbot.entity.Status;
import com.team4.happydogbot.exception.AdopterCatNotFoundException;
import com.team4.happydogbot.service.AdopterCatService;
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
 * for the class - service of the adopter of cats
 *
 * @see AdopterCat
 * @see AdopterCatService
 * @see AdopterCatController
 * @see AdopterCatControllerTest
 */
@WebMvcTest(AdopterCatController.class)
public class AdopterCatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AdopterCatService adopterCatService;

    private final AdopterCat expected = new AdopterCat();
    private final AdopterCat expected1 = new AdopterCat();
    private final AdopterCat actual = new AdopterCat();
    private final AdopterCat exceptionAdopterCat = new AdopterCat();

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

        exceptionAdopterCat.setChatId(0L);
        exceptionAdopterCat.setFirstName(" ");
        exceptionAdopterCat.setLastName(null);
        exceptionAdopterCat.setUserName(null);
        exceptionAdopterCat.setAge(0);
        exceptionAdopterCat.setAddress("");
        exceptionAdopterCat.setTelephoneNumber(null);
        exceptionAdopterCat.setState(null);
    }

    /**
     * Method Testing <b>add()</b> в AdopterCatController
     * <br>
     * Mockito: when the method is called <b>AdopterCatService::add</b>,
     * return status 200 and adopter of the cat <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for status 200 and return of the adopter cat " +
            "when trying to create it and store it in the database")
    void addAdopterCatTest200() throws Exception {
        when(adopterCatService.add(expected)).thenReturn(expected);

        mockMvc.perform(
                        post("/adopter_cat")
                                .content(objectMapper.writeValueAsString(expected))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    /**
     * Method Testing <b>get()</b> в AdopterCatController
     * <br>
     * Mockito: when the method is called <b>AdopterCatService::get</b>,
     * return status 200 and adopter of the cat <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for getting status 200 and returning the adopter of the cat when trying to search for it by chatId")
    public void getAdopterCatTest200() throws Exception {
        Long chatId = expected.getChatId();

        when(adopterCatService.get(anyLong())).thenReturn(expected);

        mockMvc.perform(
                        get("/adopter_cat/{chatId}", chatId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(chatId));
    }

    /**
     * Method Testing <b>get()</b> в AdopterCatController
     * <br>
     * Mockito: when the method is called <b>AdopterCatService::get</b>,
     * an exception is thrown <b>AdopterCatNotFoundException</b> и
     * return status 404 <b>exceptionAdopterCat</b>
     *
     * @throws Exception
     * @throws AdopterCatNotFoundException
     */
    @Test
    @DisplayName("Checking for status 404 when trying to search by chatId " +
            "an adopter of a cat that is not in the database")
    void getAdopterCatTest404() throws Exception {
        when(adopterCatService.get(anyLong())).thenThrow(AdopterCatNotFoundException.class);

        mockMvc.perform(
                        get("/adopter_cat/{chatId}", exceptionAdopterCat.getChatId().toString()))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>delete()</b> в AdopterCatController
     * <br>
     * Mockito: when the method is called <b>AdopterCatService::remove</b>,
     * return status 200 <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for status 200 when trying to remove an adopter from the database by chatId")
    public void deleteAdopterCatTest200() throws Exception {
        Long chatId = expected.getChatId();

        when(adopterCatService.remove(anyLong())).thenReturn(true);

        mockMvc.perform(
                        delete("/adopter_cat/{chatId}", chatId))
                .andExpect(status().isOk());
    }

    /**
     * Method Testing <b>delete()</b> в AdopterCatController
     * <br>
     * Mockito: when the method is called <b>AdopterCatService::remove</b>,
     * an exception is thrown <b>AdopterCatNotFoundException</b> и
     * return status 404 <b>exceptionAdopterCat</b>
     *
     * @throws Exception
     * @throws AdopterCatNotFoundException
     */
    @Test
    @DisplayName("Checking for status 404 when trying to delete by chatId " +
            "an adopter of a cat that is not in the database")
    public void deleteAdopterCatTest404() throws Exception {
        Long chatId = exceptionAdopterCat.getChatId();

        when(adopterCatService.remove(anyLong())).thenThrow(AdopterCatNotFoundException.class);

        mockMvc.perform(
                        delete("/adopter_cat/{chatId}", chatId))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>update()</b> в AdopterCatController
     * <br>
     * Mockito: when the method is called <b>AdopterCatService::update</b>,
     * return status 200 and edited cat adopter <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for status 200 when trying to update and save an adopter cat in the database")
    public void updateAdopterCatTest200() throws Exception {
        when(adopterCatService.update(expected)).thenReturn(expected);

        mockMvc.perform(
                        put("/adopter_cat")
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
     * Method Testing <b>update()</b> в AdopterCatController
     * <br>
     * Mockito: when the method is called <b>AdopterCatService::update</b>,
     * an exception is thrown <b>AdopterCatNotFoundException</b> и
     * return status 404 <b>exceptionAdopterCat</b>
     *
     * @throws Exception
     * @throws AdopterCatNotFoundException
     */
    @Test
    @DisplayName("Checking for a 404 status when trying to update and save " +
            "an adopter of a cat that is not in the database")
    public void updateAdopterCatTest404() throws Exception {
        when(adopterCatService.update(exceptionAdopterCat)).thenThrow(AdopterCatNotFoundException.class);

        mockMvc.perform(
                        put("/adopter_cat")
                                .content(objectMapper.writeValueAsString(exceptionAdopterCat))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>getAll()</b> в AdopterCatController
     * <br>
     * Mockito: when the method is called <b>AdopterCatService::getAll</b>,
     * return status 200 and collection of cat adopters <b>Arrays.asList(expected, expected1)</b>
     */
    @Test
    @DisplayName("Verification of obtaining status 200 and the return of all adopters of cats " +
            "when trying to find them in the database")
    void getAllAdopterCatsTest200() throws Exception {
        when(adopterCatService.getAll()).thenReturn(Arrays.asList(expected, expected1));

        mockMvc.perform(
                        get("/adopter_cat/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(expected, expected1))));
    }

    @Test
    @DisplayName("Checking for status 200 when sending a message to a user")
    public void sendMessageWithValidChatId() throws Exception {
        Long chatId = expected.getChatId();
        String textToSend = "Hello, world!";

        when(adopterCatService.get(chatId)).thenReturn(expected);

        mockMvc.perform(get("/adopter_cat/send_message")
                        .param("chatId", String.valueOf(chatId))
                        .param("textToSend", textToSend))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Checking for a 404 status when sending a message to a user that doesn't exist")
    public void sendMessageWithInvalidChatId() throws Exception {
        Long chatId = anyLong();
        String textToSend = "Hello, world!";
        when(adopterCatService.get(chatId)).thenReturn(null);

        mockMvc.perform(get("/adopter_cat/send_message")
                        .param("chatId", String.valueOf(chatId))
                        .param("textToSend", textToSend))
                .andExpect(status().isNotFound());
    }
}
