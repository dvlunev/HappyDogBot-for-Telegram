package com.team4.happydogbot.service;

import com.team4.happydogbot.entity.ExaminationStatus;
import com.team4.happydogbot.entity.ReportCat;
import com.team4.happydogbot.exception.ReportCatNotFoundException;
import com.team4.happydogbot.repository.ReportCatRepository;
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
 * Test class to test CRUD operations in cat report service class
 *
 * @see ReportCat
 * @see ReportCatRepository
 * @see ReportCatService
 * @see ReportCatServiceTest
 */
@ExtendWith(MockitoExtension.class)
public class ReportCatServiceTest {

    @Mock
    ReportCatRepository reportCatRepository;
    @InjectMocks
    ReportCatService reportCatService;

    private final ReportCat expected = new ReportCat();
    private final ReportCat expected1 = new ReportCat();

    @BeforeEach
    public void setUp() {
        expected.setId(1L);
        expected.setReportDate(LocalDate.of(2023, 3, 24));
        expected.setFileId("Test124578");
        expected.setCaption("Рацион: гуд; Самочувствие: гуд; Поведение: гуд");
        expected.setExamination(ExaminationStatus.UNCHECKED);

        expected1.setId(1L);
        expected1.setReportDate(LocalDate.of(2023, 3, 24));
        expected1.setFileId("Test986532");
        expected1.setCaption("Рацион: гуд; Самочувствие: гуд; Поведение: гуд");
        expected1.setExamination(ExaminationStatus.ACCEPTED);
    }

    /**
     * Method Testing <b>add()</b> in ReportCatService
     * <br>
     * Mockito: when the method is called <b>ReportCatRepository::save</b>,
     * cat report returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking to add a new cat report and save it to the database")
    public void addReportCatTest() {
        when(reportCatRepository.save(any(ReportCat.class))).thenReturn(expected);

        ReportCat actual = reportCatService.add(expected);

        Assertions.assertThat(actual.getReportDate()).isEqualTo(expected.getReportDate());
        Assertions.assertThat(actual.getFileId()).isEqualTo(expected.getFileId());
        Assertions.assertThat(actual.getCaption()).isEqualTo(expected.getCaption());
        Assertions.assertThat(actual.getExamination()).isEqualTo(expected.getExamination());
    }

    /**
     * Method Testing <b>get()</b> in ReportCatService
     * <br>
     * Mockito: when the method is called <b>ReportCatRepository::findById</b>,
     * cat report returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking if a cat report is searched for by id and returned from the database")
    public void getByIdReportCatTest() {
        when(reportCatRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));

        ReportCat actual = reportCatService.get(expected.getId());

        Assertions.assertThat(actual.getReportDate()).isEqualTo(expected.getReportDate());
        Assertions.assertThat(actual.getFileId()).isEqualTo(expected.getFileId());
        Assertions.assertThat(actual.getCaption()).isEqualTo(expected.getCaption());
        Assertions.assertThat(actual.getExamination()).isEqualTo(expected.getExamination());
    }

    /**
     * Test for throwing an exception in a method <b>get()</b> in ReportCatService
     * <br>
     * Mockito: when the method is called <b>ReportCatRepository::findById</b>,
     * an exception is thrown <b>ReportCatNotFoundException</b>
     *
     * @throws ReportCatNotFoundException
     */
    @Test
    @DisplayName("Checking for exception throw in cat report search method")
    public void getByIdReportCatExceptionTest() {
        when(reportCatRepository.findById(any(Long.class))).thenThrow(ReportCatNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(ReportCatNotFoundException.class,
                () -> reportCatService.get(0L));
    }

    /**
     * Method Testing <b>update()</b> in ReportCatService
     * <br>
     * Mockito: when the method is called <b>ReportCatRepository::findById</b> и <b>ReportCatRepository::save</b>,
     * edited cat report returns <b>expected1</b>
     */
    @Test
    @DisplayName("Checking the editing of the cat report, saving and returning it from the database")
    public void updateReportCatTest() {
        when(reportCatRepository.findById(any(Long.class))).thenReturn(Optional.of(expected1));
        when(reportCatRepository.save(any(ReportCat.class))).thenReturn(expected1);

        ReportCat actual = reportCatService.update(expected1);

        Assertions.assertThat(actual.getReportDate()).isEqualTo(expected1.getReportDate());
        Assertions.assertThat(actual.getFileId()).isEqualTo(expected1.getFileId());
        Assertions.assertThat(actual.getCaption()).isEqualTo(expected1.getCaption());
        Assertions.assertThat(actual.getExamination()).isEqualTo(expected1.getExamination());
    }

    /**
     * Test for throwing an exception in a method <b>update()</b> in ReportCatService
     * <br>
     * Mockito: when the method is called <b>ReportCatRepository::findById</b>,
     * an exception is thrown <b>ReportCatNotFoundException</b>
     *
     * @throws ReportCatNotFoundException
     */
    @Test
    @DisplayName("Checking for an exception throw in the cat report ddit method")
    public void updateReportCatExceptionTest() {
        when(reportCatRepository.findById(any(Long.class))).thenThrow(ReportCatNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(ReportCatNotFoundException.class,
                () -> reportCatService.update(expected1));
    }

    /**
     * Method Testing <b>getAll()</b> in ReportCatService
     * <br>
     * Mockito: when the method is called <b>ReportCatRepository::findAll</b>,
     * cat report collection returns <b>reportCats</b>
     */
    @Test
    @DisplayName("Checking to find all cat reports and return them from the database")
    public void getAllReportCatsTest() {
        List<ReportCat> reportCats = new ArrayList<>();
        reportCats.add(expected);
        reportCats.add(expected1);

        when(reportCatRepository.findAll()).thenReturn(reportCats);

        Collection<ReportCat> actual = reportCatService.getAll();

        Assertions.assertThat(actual.size()).isEqualTo(reportCats.size());
        Assertions.assertThat(actual).isEqualTo(reportCats);
    }

    /**
     * Method Testing <b>getAll()</b> in ReportCatService
     * <br>
     * Mockito: when the method is called <b>ReportCatRepository::findAll</b>,
     * returning an empty cat report collection <b>reportCats</b>
     */
    @Test
    @DisplayName("Checking to find all cat reports and return an empty list from the database")
    public void getAllReportCatsTestReturnsEmpty() {
        List<ReportCat> reportCats = new ArrayList<>();
        when(reportCatRepository.findAll()).thenReturn(reportCats);
        assertThat(reportCatService.getAll()).isEqualTo(reportCats);
    }
}
