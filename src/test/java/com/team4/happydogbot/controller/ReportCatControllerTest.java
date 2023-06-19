package com.team4.happydogbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.happydogbot.entity.ExaminationStatus;
import com.team4.happydogbot.entity.ReportCat;
import com.team4.happydogbot.exception.ReportCatNotFoundException;
import com.team4.happydogbot.service.ReportCatService;
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
 * for class - cat reporting service
 *
 * @see ReportCat
 * @see ReportCatService
 * @see ReportCatController
 * @see ReportCatControllerTest
 */
@WebMvcTest(ReportCatController.class)
public class ReportCatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ReportCatService reportCatService;

    private final ReportCat expected = new ReportCat();
    private final ReportCat expected1 = new ReportCat();
    private final ReportCat actual = new ReportCat();
    private final ReportCat exceptionReportCat = new ReportCat();

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
        actual.setExamination(ExaminationStatus.UNCHECKED);

        exceptionReportCat.setId(0L);
        exceptionReportCat.setReportDate(LocalDate.of(2000, 1, 1));
        exceptionReportCat.setFileId(" ");
        exceptionReportCat.setCaption(null);
        exceptionReportCat.setExamination(null);
    }

    /**
     * Method Testing <b>add()</b> в ReportCatController
     * <br>
     * Mockito: when the method is called <b>ReportCatService::add</b>,
     * returns status 200 and cat report <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for Status 200 Receipt and Cat Report Return" +
            "when trying to create it and store it in the database")
    void addReportCatTest200() throws Exception {
        when(reportCatService.add(expected)).thenReturn(expected);

        mockMvc.perform(
                        post("/report_cat")
                                .content(objectMapper.writeValueAsString(expected))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    /**
     * Method Testing <b>get()</b> в ReportCatController
     * <br>
     * Mockito: when the method is called <b>ReportCatService::get</b>,
     * returns status 200 and cat report <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for getting status 200 and returning a report about a cat when trying to search for it by id")
    public void getReportCatTest200() throws Exception {
        Long id = expected.getId();

        when(reportCatService.get(anyLong())).thenReturn(expected);

        mockMvc.perform(
                        get("/report_cat/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    /**
     * Method Testing <b>get()</b> в ReportCatController
     * <br>
     * Mockito: when the method is called <b>ReportCatService::get</b>,
     * an exception is thrown <b>ReportCatNotFoundException</b> и
     * returns status 404 <b>exceptionReportCat</b>
     *
     * @throws Exception
     * @throws ReportCatNotFoundException
     */
    @Test
    @DisplayName("Checking for status 404 when trying to search by id" +
            "a report about a cat that is not in the database")
    void getReportCatTest404() throws Exception {
        when(reportCatService.get(anyLong())).thenThrow(ReportCatNotFoundException.class);

        mockMvc.perform(
                        get("/report_cat/{id}", exceptionReportCat.getId().toString()))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>getPhoto()</b> в ReportCatController
     * <br>
     * Mockito: when the method is called <b>ReportCatService::get</b>,
     * returns status 200 and cat report <b>expected</b>
     * Mockito: when the method is called <b>ReportCatService::getFile</b>,
     * return status 200 and file bytes <b>fileContent</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for getting status 200 and returning a photo report about a cat when trying to search for it by id")
    public void getPhotoTest200() throws Exception {
        Long id = expected.getId();
        byte[] fileContent = "test photo file".getBytes();
        ReportCat reportCat = new ReportCat();
        reportCat.setId(id);
        reportCat.setFileId(Arrays.toString(fileContent));

        when(reportCatService.get(anyLong())).thenReturn(expected);
        when(reportCatService.getFile(anyLong())).thenReturn(fileContent);

        mockMvc.perform(
                        get("/report_cat/photo/{id}", id))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"ReportPhoto.jpg\""))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/octet-stream"))
                .andExpect(content().bytes(fileContent));
    }

    /**
     * Method Testing <b>delete()</b> в ReportCatController
     * <br>
     * Mockito: when the method is called <b>ReportCatService::remove</b>,
     * returns status 200 <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for status 200 when trying to delete a cat report from the database by id")
    public void deleteReportCatTest200() throws Exception {
        Long id = expected.getId();

        when(reportCatService.remove(anyLong())).thenReturn(true);

        mockMvc.perform(
                        delete("/report_cat/{id}", id))
                .andExpect(status().isOk());
    }

    /**
     * Method Testing <b>delete()</b> в ReportCatController
     * <br>
     * Mockito: when the method is called <b>ReportCatService::remove</b>,
     * an exception is thrown <b>ReportCatNotFoundException</b> и
     * returns status 404 <b>exceptionReportCat</b>
     *
     * @throws Exception
     * @throws ReportCatNotFoundException
     */
    @Test
    @DisplayName("Checking for status 404 when trying to delete by id" +
            "report about a cat that is not in the database")
    public void deleteReportCatTest404() throws Exception {
        Long id = exceptionReportCat.getId();

        when(reportCatService.remove(anyLong())).thenThrow(ReportCatNotFoundException.class);

        mockMvc.perform(
                        delete("/report_cat/{id}", id))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>update()</b> в ReportCatController
     * <br>
     * Mockito: when the method is called <b>ReportCatService::update</b>,
     * returns status 200 and edited cat report <b>expected</b>
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Checking for getting status 200 when trying to update and save the cat report to the database")
    public void updateReportCatTest200() throws Exception {
        when(reportCatService.update(expected)).thenReturn(expected);

        mockMvc.perform(
                        put("/report_cat")
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
     * Method Testing <b>update()</b> в ReportCatController
     * <br>
     * Mockito: when the method is called <b>ReportCatService::update</b>,
     * an exception is thrown <b>ReportCatNotFoundException</b> и
     * returns status 404 <b>exceptionReportCat</b>
     *
     * @throws Exception
     * @throws ReportCatNotFoundException
     */
    @Test
    @DisplayName("Checking for a 404 status when trying to update and save" +
            "report about a cat that is not in the database")
    public void updateReportCatTest404() throws Exception {
        when(reportCatService.update(exceptionReportCat)).thenThrow(ReportCatNotFoundException.class);

        mockMvc.perform(
                        put("/report_cat")
                                .content(objectMapper.writeValueAsString(exceptionReportCat))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Method Testing <b>getAll()</b> в ReportCatController
     * <br>
     * Mockito: when the method is called <b>ReportCatService::getAll</b>,
     * returns status 200 and cat reports collection <b>Arrays.asList(expected, expected1)</b>
     */
    @Test
    @DisplayName("Checking for Status 200 Receipt and Return of All Cat Reports" +
            "when trying to find them in the database")
    void getAllReportCatsTest200() throws Exception {
        when(reportCatService.getAll()).thenReturn(Arrays.asList(expected, expected1));

        mockMvc.perform(
                        get("/report_cat/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(expected, expected1))));
    }
}
