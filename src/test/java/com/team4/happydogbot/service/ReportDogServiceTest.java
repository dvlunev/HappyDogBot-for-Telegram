package com.team4.happydogbot.service;

import com.team4.happydogbot.entity.ExaminationStatus;
import com.team4.happydogbot.entity.ReportDog;
import com.team4.happydogbot.exception.ReportDogNotFoundException;
import com.team4.happydogbot.repository.ReportDogRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Test class to test CRUD operations in dog report service class
 *
 * @see ReportDog
 * @see ReportDogRepository
 * @see ReportDogService
 * @see ReportDogServiceTest
 */
@ExtendWith(MockitoExtension.class)
public class ReportDogServiceTest {

    @Mock
    private ReportDogRepository reportDogRepository;
    @InjectMocks
    private ReportDogService reportDogService;

    private final ReportDog expected = new ReportDog();
    private final ReportDog expected1 = new ReportDog();

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
    }

    /**
     * Method Testing <b>add()</b> in ReportDogService
     * <br>
     * Mockito: when the method is called <b>ReportDogRepository::save</b>,
     * dog report returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking to add a new dog report and save it to the database")
    public void addReportDogTest() {
        when(reportDogRepository.save(any(ReportDog.class))).thenReturn(expected);

        ReportDog actual = reportDogService.add(expected);

        Assertions.assertThat(actual.getReportDate()).isEqualTo(expected.getReportDate());
        Assertions.assertThat(actual.getFileId()).isEqualTo(expected.getFileId());
        Assertions.assertThat(actual.getCaption()).isEqualTo(expected.getCaption());
        Assertions.assertThat(actual.getExamination()).isEqualTo(expected.getExamination());
    }

    /**
     * Method Testing <b>get()</b> in ReportDogService
     * <br>
     * Mockito: when the method is called <b>ReportDogRepository::findById</b>,
     * dog report returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking if a dog report is searched for by id and returned from the database")
    public void getByIdReportDogTest() {
        when(reportDogRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));

        ReportDog actual = reportDogService.get(expected.getId());

        Assertions.assertThat(actual.getReportDate()).isEqualTo(expected.getReportDate());
        Assertions.assertThat(actual.getFileId()).isEqualTo(expected.getFileId());
        Assertions.assertThat(actual.getCaption()).isEqualTo(expected.getCaption());
        Assertions.assertThat(actual.getExamination()).isEqualTo(expected.getExamination());
    }

    /**
     * Test for throwing an exception in a method <b>get()</b> in ReportDogService
     * <br>
     * Mockito: when the method is called <b>ReportDogRepository::findById</b>,
     * an exception is thrown <b>ReportDogNotFoundException</b>
     *
     * @throws ReportDogNotFoundException
     */
    @Test
    @DisplayName("Checking for exception throw in dog report search method")
    public void getByIdReportDogExceptionTest() {
        when(reportDogRepository.findById(any(Long.class))).thenThrow(ReportDogNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(ReportDogNotFoundException.class,
                () -> reportDogService.get(0L));
    }

    /**
     * Method Testing <b>update()</b> in ReportDogService
     * <br>
     * Mockito: when the method is called <b>ReportDogRepository::findById</b> и <b>ReportDogRepository::save</b>,
     * edited dog report returns <b>expected1</b>
     */
    @Test
    @DisplayName("Checking the editing of the dog report, saving and returning it from the database")
    public void updateReportDogTest() {
        when(reportDogRepository.findById(any(Long.class))).thenReturn(Optional.of(expected1));
        when(reportDogRepository.save(any(ReportDog.class))).thenReturn(expected1);

        ReportDog actual = reportDogService.update(expected1);

        Assertions.assertThat(actual.getReportDate()).isEqualTo(expected1.getReportDate());
        Assertions.assertThat(actual.getFileId()).isEqualTo(expected1.getFileId());
        Assertions.assertThat(actual.getCaption()).isEqualTo(expected1.getCaption());
        Assertions.assertThat(actual.getExamination()).isEqualTo(expected1.getExamination());
    }

    /**
     * Test for throwing an exception in a method <b>update()</b> in ReportDogService
     * <br>
     * Mockito: when the method is called <b>ReportDogRepository::findById</b>,
     * an exception is thrown <b>ReportDogNotFoundException</b>
     *
     * @throws ReportDogNotFoundException
     */
    @Test
    @DisplayName("Checking for an exception throw in the dog report edit method")
    public void updateReportDogExceptionTest() {
        when(reportDogRepository.findById(any(Long.class))).thenThrow(ReportDogNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(ReportDogNotFoundException.class,
                () -> reportDogService.update(expected1));
    }

    /**
     * Method Testing <b>getAll()</b> in ReportDogService
     * <br>
     * Mockito: when the method is called <b>ReportDogRepository::findAll</b>,
     * dog report collection returns <b>reportDogs</b>
     */
    @Test
    @DisplayName("Checking to find all dog reports and return them from the database")
    public void getAllReportDogsTest() {
        List<ReportDog> reportDogs = new ArrayList<>();
        reportDogs.add(expected);
        reportDogs.add(expected1);

        when(reportDogRepository.findAll()).thenReturn(reportDogs);

        Collection<ReportDog> actual = reportDogService.getAll();

        Assertions.assertThat(actual.size()).isEqualTo(reportDogs.size());
        Assertions.assertThat(actual).isEqualTo(reportDogs);
    }

    /**
     * Method Testing <b>getAll()</b> in ReportDogService
     * <br>
     * Mockito: when the method is called <b>ReportDogRepository::findAll</b>,
     * returning an empty dog report collection <b>adopterDogs</b>
     */
    @Test
    @DisplayName("Checking to find all dog reports and return an empty list from the database")
    public void getAllReportDogsTestReturnsEmpty() {
        List<ReportDog> reportDogs = new ArrayList<>();
        when(reportDogRepository.findAll()).thenReturn(reportDogs);
        assertThat(reportDogService.getAll()).isEqualTo(reportDogs);
    }
}
