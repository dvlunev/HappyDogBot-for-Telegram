package com.team4.happydogbot.service;

import com.team4.happydogbot.entity.*;
import com.team4.happydogbot.exception.AdopterCatNotFoundException;
import com.team4.happydogbot.repository.AdopterCatRepository;
import com.team4.happydogbot.repository.CatRepository;
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
 * Test class to test CRUD operations in the cat's adopter service class
 *
 * @see AdopterCat
 * @see AdopterCatRepository
 * @see AdopterCatService
 * @see AdopterCatServiceTest
 */
@ExtendWith(MockitoExtension.class)
public class AdopterCatServiceTest {

    @Mock
    AdopterCatRepository adopterCatRepository;

    @Mock
    CatRepository catRepository;

    @InjectMocks
    AdopterCatService adopterCatService;

    private final AdopterCat expected = new AdopterCat();
    private final AdopterCat expected1 = new AdopterCat();

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
        expected.setCat(new Cat(1L, "Мурзик", "Сиамская", 2021, "Черно-белый"));

        expected1.setChatId(9876543210L);
        expected1.setFirstName("Petr");
        expected1.setLastName("Petrov");
        expected1.setUserName("pppetrov");
        expected1.setAge(23);
        expected1.setAddress("МСК...");
        expected1.setTelephoneNumber("7902...");
        expected1.setState(Status.REGISTRATION);
    }

    /**
     * Method Testing <b>add()</b> в AdopterCatService
     * <br>
     * Mockito: when the method is called <b>AdopterCatRepository::save</b>,
     * cat adopter returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking the addition of a new adopter cat and saving it in the database")
    public void addAdopterCatTest() {
        when(adopterCatRepository.save(any(AdopterCat.class))).thenReturn(expected);

        AdopterCat actual = adopterCatService.add(expected);

        Assertions.assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        Assertions.assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        Assertions.assertThat(actual.getUserName()).isEqualTo(expected.getUserName());
        Assertions.assertThat(actual.getAge()).isEqualTo(expected.getAge());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(actual.getTelephoneNumber()).isEqualTo(expected.getTelephoneNumber());
        Assertions.assertThat(actual.getState()).isEqualTo(expected.getState());
    }

    /**
     * Method Testing <b>get()</b> в AdopterCatService
     * <br>
     * Mockito: when the method is called <b>AdopterCatRepository::findById</b>,
     * cat adopter returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking the search for the cat's adopter by id and returning it from the database")
    public void getByIdAdopterCatTest() {
        when(adopterCatRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));

        AdopterCat actual = adopterCatService.get(expected.getChatId());

        Assertions.assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        Assertions.assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        Assertions.assertThat(actual.getUserName()).isEqualTo(expected.getUserName());
        Assertions.assertThat(actual.getAge()).isEqualTo(expected.getAge());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(actual.getTelephoneNumber()).isEqualTo(expected.getTelephoneNumber());
        Assertions.assertThat(actual.getState()).isEqualTo(expected.getState());
    }

    /**
     * Test for throwing an exception in a method <b>get()</b> в AdopterCatService
     * <br>
     * Mockito: when the method is called <b>AdopterCatRepository::findById</b>,
     * an exception is thrown <b>AdopterCatNotFoundException</b>
     *
     * @throws AdopterCatNotFoundException
     */
    @Test
    @DisplayName("Checking for an Exception Throw in the Cat Adopter Finder Method")
    public void getByIdAdopterCatExceptionTest() {
        when(adopterCatRepository.findById(any(Long.class))).thenThrow(AdopterCatNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(AdopterCatNotFoundException.class,
                () -> adopterCatService.get(0L));
    }

    /**
     * Method Testing <b>update()</b> в AdopterCatService
     * <br>
     * Mockito: when the method is called <b>AdopterCatRepository::findById</b> и <b>AdopterCatRepository::save</b>,
     * edited cat adopter returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking the editing of the cat's adopter, saving and returning it from the database")
    public void updateAdopterCatTest() {
        when(adopterCatRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));
        when(adopterCatRepository.save(any(AdopterCat.class))).thenReturn(expected);
        when(catRepository.findById(any(Long.class))).thenReturn(Optional.of(expected.getCat()));

        AdopterCat actual = adopterCatService.update(expected);

        Assertions.assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        Assertions.assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        Assertions.assertThat(actual.getUserName()).isEqualTo(expected.getUserName());
        Assertions.assertThat(actual.getAge()).isEqualTo(expected.getAge());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(actual.getTelephoneNumber()).isEqualTo(expected.getTelephoneNumber());
        Assertions.assertThat(actual.getState()).isEqualTo(expected.getState());
    }

    /**
     * Test for throwing an exception in a method <b>update()</b> в AdopterCatService
     * <br>
     * Mockito: when the method is called <b>AdopterCatRepository::findById</b>,
     * an exception is thrown <b>AdopterCatNotFoundException</b>
     *
     * @throws AdopterCatNotFoundException
     */
    @Test
    @DisplayName("Checking for an exception being thrown in the cat's adopter edit method")
    public void updateAdopterCatExceptionTest() {
        when(adopterCatRepository.findById(any(Long.class))).thenThrow(AdopterCatNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(AdopterCatNotFoundException.class,
                () -> adopterCatService.update(expected));
    }

    /**
     * Method Testing <b>getAll()</b> в AdopterCatService
     * <br>
     * Mockito: when the method is called <b>AdopterCatRepository::findAll</b>,
     * cat adopters collection returns <b>adopterCats</b>
     */
    @Test
    @DisplayName("Verification of finding all adopters of cats and returning them from the database")
    public void getAllAdopterCatsTest() {
        List<AdopterCat> adopterCats = new ArrayList<>();
        adopterCats.add(expected);
        adopterCats.add(expected1);

        when(adopterCatRepository.findAll()).thenReturn(adopterCats);

        Collection<AdopterCat> actual = adopterCatService.getAll();

        Assertions.assertThat(actual.size()).isEqualTo(adopterCats.size());
        Assertions.assertThat(actual).isEqualTo(adopterCats);
    }

    /**
     * Method Testing <b>getAll()</b> в AdopterCatService
     * <br>
     * Mockito: when the method is called <b>AdopterCatRepository::findAll</b>,
     * empty cat adopter collection returns <b>adopterCats</b>
     */
    @Test
    @DisplayName("Checking to find all cat adopters and return an empty list from the database")
    public void getAllAdopterCatsTestReturnsEmpty() {
        List<AdopterCat> adopterCats = new ArrayList<>();
        when(adopterCatRepository.findAll()).thenReturn(adopterCats);
        assertThat(adopterCatService.getAll()).isEqualTo(adopterCats);
    }

    /**
     * Method Testing <b>update()</b> в AdopterCatService
     * <br>
     * Mockito: when the method is called <b>AdopterCatRepository::findById</b> и <b>AdopterCatRepository::save</b>,
     * edited adopter returns with cat <b>expected</b>
     */
    @Test
    @DisplayName("Checking if a cat is added as a field of the adopter object")
    public void updateFieldCatIdByAdopterCat() {
        expected.setCat(new Cat(1L, "Ponchik", "Bolinez", 2020, "Test1"));
        when(adopterCatRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));
        when(adopterCatRepository.save(any(AdopterCat.class))).thenReturn(expected);
        when(catRepository.findById(any(Long.class))).thenReturn(Optional.of(expected.getCat()));

        AdopterCat actual = adopterCatService.update(expected);

        Assertions.assertThat(actual.getChatId()).isEqualTo(expected.getChatId());
        Assertions.assertThat(actual.getCat()).isEqualTo(expected.getCat());
    }

    /**
     * Method Testing <b>update()</b> в AdopterCatService
     * <br>
     * Mockito: when the method is called <b>AdopterCatRepository::findById</b> и <b>AdopterCatRepository::save</b>,
     * edited adopter returns with collection of cat reports <b>adopterCats</b>
     */
    @Test
    @DisplayName("Validate adding a list of reports as a field of the adopter object")
    public void updateFieldReportAdopterCat() {
        ReportCat expectedTest1 = new ReportCat();
        expectedTest1.setId(1L);
        expectedTest1.setReportDate(LocalDate.of(2023, 3, 24));
        expectedTest1.setFileId("Test124578");
        expectedTest1.setCaption("Рацион: гуд; Самочувствие: гуд; Поведение: гуд");
        expectedTest1.setExamination(ExaminationStatus.UNCHECKED);

        ReportCat expectedTest2 = new ReportCat();
        expectedTest2.setId(2L);
        expectedTest2.setReportDate(LocalDate.of(2023, 3, 24));
        expectedTest2.setFileId("Test986532");
        expectedTest2.setCaption("Рацион: гуд; Самочувствие: гуд; Поведение: гуд");
        expectedTest2.setExamination(ExaminationStatus.ACCEPTED);

        List<ReportCat> reportCats = new ArrayList<>();
        reportCats.add(expectedTest1);
        reportCats.add(expectedTest2);

        expected.setReports(reportCats);

        when(adopterCatRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));
        when(adopterCatRepository.save(any(AdopterCat.class))).thenReturn(expected);
        when(catRepository.findById(any(Long.class))).thenReturn(Optional.of(expected.getCat()));

        AdopterCat actual = adopterCatService.update(expected);

        Assertions.assertThat(actual.getChatId()).isEqualTo(expected.getChatId());
        Assertions.assertThat(actual.getReports()).isEqualTo(expected.getReports());
    }
}
