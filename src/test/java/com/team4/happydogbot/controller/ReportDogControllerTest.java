package com.team4.happydogbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.happydogbot.entity.ExaminationStatus;
import com.team4.happydogbot.entity.ReportDog;
import com.team4.happydogbot.exception.ReportDogNotFoundException;
import com.team4.happydogbot.service.ReportDogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for checking API endpoints when accessing routes with separate HTTP methods
 * for class - dog reporting service
 *
 * @see ReportDog
 * @see ReportDogService
 * @see ReportDogController
 * @see ReportDogControllerTest
 */
@WebMvcTest(ReportDogController.class)
public class ReportDogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ReportDogService reportDogService;

    private final ReportDog expected = new ReportDog();
    private final ReportDog expected1 = new ReportDog();
    private final ReportDog actual = new ReportDog();
    private final ReportDog exceptionReportDog = new ReportDog();

    @BeforeEach
    public void setUp() {
        expected.setId(1L);
        expected.setReportDate(LocalDate.of(2023, 3, 24));
        expected.setFileId("Test124578");
        expected.setCaption("Рацион: гуд; Самочувствие: гуд; Поведение: гуд");
        expected.setExamination(ExaminationStatus.UNCHECKED);

        expected1.setId(2L);
        expected1.setReportDate(LocalDate.of(2023, 3, 24));
        expected1.setFileId("Test986532");
        expected1.setCaption("Рацион: гуд; Самочувствие: гуд; Поведение: гуд");
        expected1.setExamination(ExaminationStatus.ACCEPTED);

        actual.setId(1L);
        actual.setReportDate(LocalDate.of(2023, 3, 24));
        actual.setFileId("Test124578");
        actual.setCaption("Рацион: гуд; Самочувствие: гуд; Поведение: бэд");
        actual.setExamination(ExaminationStatus.REJECTED);

        exceptionReportDog.setId(0L);
        exceptionReportDog.setReportDate(LocalDate.of(2000, 1, 1));
        exceptionReportDog.setFileId(" ");
        exceptionReportDog.setCaption(null);
        exceptionReportDog.setExamination(null);
    }

    /**
     * Method Testing <b>add()</b> в ReportDogController
     * <br>
     * Mockito: when the method is called <b>ReportDogService::add</b>,
     * returns status 200 and dog report <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for Status 200 Receipt and Dog Report Return" +
            "when trying to create it and store it in the database")
    void addReportDogTest200() throws Exception {
        when(reportDogService.add(expected)).thenReturn(expected);

        mockMvc.perform(
                        post("/report_dog")
                                .content(objectMapper.writeValueAsString(expected))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    /**
     * Method Testing <b>get()</b> в ReportDogController
     * <br>
     * Mockito: when the method is called <b>ReportDogService::get</b>,
     * returns status 200 and dog report <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for getting status 200 and returning a report about a dog when trying to search for it by id")
    public void getReportDogTest200() throws Exception {
        Long id = expected.getId();

        when(reportDogService.get(anyLong())).thenReturn(expected);

        mockMvc.perform(
                        get("/report_dog/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    /**
     * Method Testing <b>get()</b> в ReportDogController
     * <br>
     * Mockito: when the method is called <b>ReportDogService::get</b>,
     * an exception is thrown <b>ReportDogNotFoundException</b> и
     * returns status 404 <b>exceptionReportDog</b>
     *
     * @throws Exception
     * @throws ReportDogNotFoundException
     */
    @Test
    @DisplayName("Checking for status 404 when trying to search by id" +
            "a report about a dog that is not in the database")
    void getReportDogTest404() throws Exception {
        when(reportDogService.get(anyLong())).thenThrow(ReportDogNotFoundException.class);

        mockMvc.perform(
                        get("/report_dog/{id}", exceptionReportDog.getId().toString()))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>getPhoto()</b> в ReportDogController
     * <br>
     * Mockito: when the method is called <b>ReportDogService::get</b>,
     * returns status 200 and dog report <b>expected</b>
     * Mockito: when the method is called <b>ReportDogService::getFile</b>,
     * return status 200 and file bytes <b>fileContent</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for getting status 200 and returning a photo report about a dog when trying to search for it by id")
    public void getPhotoTest200() throws Exception {
        Long id = expected.getId();
        byte[] fileContent = "test photo file".getBytes();
        ReportDog reportDog = new ReportDog();
        reportDog.setId(id);
        reportDog.setFileId(Arrays.toString(fileContent));

        when(reportDogService.get(anyLong())).thenReturn(expected);
        when(reportDogService.getFile(anyLong())).thenReturn(fileContent);

        mockMvc.perform(
                        get("/report_dog/photo/{id}", id))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"ReportPhoto.jpg\""))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/octet-stream"))
                .andExpect(content().bytes(fileContent));
    }

    /**
     * Method Testing <b>delete()</b> в ReportDogController
     * <br>
     * Mockito: when the method is called <b>ReportDogService::remove</b>,
     * returns status 200 <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for status 200 when trying to delete a dog report from the database by id")
    public void deleteReportDogTest200() throws Exception {
        Long id = expected.getId();

        when(reportDogService.remove(anyLong())).thenReturn(true);

        mockMvc.perform(
                        delete("/report_dog/{id}", id))
                .andExpect(status().isOk());
    }

    /**
     * Method Testing <b>delete()</b> в ReportDogController
     * <br>
     * Mockito: when the method is called <b>ReportDogService::remove</b>,
     * an exception is thrown <b>ReportDogNotFoundException</b> и
     * returns status 404 <b>exceptionReportDog</b>
     *
     * @throws Exception
     * @throws ReportDogNotFoundException
     */
    @Test
    @DisplayName("Checking for status 404 when trying to delete by id" +
            "report about a dog that is not in the database")
    public void deleteReportDogTest404() throws Exception {
        Long id = exceptionReportDog.getId();

        when(reportDogService.remove(anyLong())).thenThrow(ReportDogNotFoundException.class);

        mockMvc.perform(
                        delete("/report_dog/{id}", id))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>update()</b> в ReportDogController
     * <br>
     * Mockito: when the method is called <b>ReportDogService::update</b>,
     * returns status 200 and edited dog report <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for getting status 200 when trying to update and save the dog report to the database")
    public void updateReportDogTest200() throws Exception {
        when(reportDogService.update(expected)).thenReturn(expected);

        mockMvc.perform(
                        put("/report_dog")
                                .content(objectMapper.writeValueAsString(actual))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(actual.getId()))
                .andExpect(jsonPath("$.reportDate").value(actual.getReportDate().toString()))
                .andExpect(jsonPath("$.fileId").value(actual.getFileId()))
                .andExpect(jsonPath("$.caption").value(actual.getCaption()))
                .andExpect(jsonPath("$.examination").value(actual.getExamination().name()));
    }

    /**
     * Method Testing <b>update()</b> в ReportDogController
     * <br>
     * Mockito: when the method is called <b>ReportDogService::update</b>,
     * an exception is thrown <b>ReportDogNotFoundException</b> и
     * returns status 404 <b>exceptionReportDog</b>
     *
     * @throws Exception
     * @throws ReportDogNotFoundException
     */
    @Test
    @DisplayName("Checking for a 404 status when trying to update and save" +
            "report about a dog that is not in the database")
    public void updateReportDogTest404() throws Exception {
        when(reportDogService.update(exceptionReportDog)).thenThrow(ReportDogNotFoundException.class);

        mockMvc.perform(
                        put("/report_dog")
                                .content(objectMapper.writeValueAsString(exceptionReportDog))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>getAll()</b> в ReportDogController
     * <br>
     * Mockito: when the method is called <b>ReportDogService::getAll</b>,
     * returns status 200 and dog reports collection <b>Arrays.asList(expected, expected1)</b>
     */
    @Test
    @DisplayName("Checking for Status 200 Receipt and Return of All Dog Reports" +
            "when trying to find them in the database")
    void getAllReportDogsTest200() throws Exception {
        when(reportDogService.getAll()).thenReturn(Arrays.asList(expected, expected1));

        mockMvc.perform(
                        get("/report_dog/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(expected, expected1))));
    }
}
