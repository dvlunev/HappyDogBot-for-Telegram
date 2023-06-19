package com.team4.happydogbot.service;

import com.team4.happydogbot.entity.*;
import com.team4.happydogbot.exception.AdopterDogNotFoundException;
import com.team4.happydogbot.repository.AdopterDogRepository;
import com.team4.happydogbot.repository.DogRepository;
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
 * Test class to test CRUD operations in the dog's adopter service class
 *
 * @see AdopterDog
 * @see AdopterDogRepository
 * @see AdopterDogService
 * @see AdopterDogServiceTest
 */
@ExtendWith(MockitoExtension.class)
public class AdopterDogServiceTest {

    @Mock
    AdopterDogRepository adopterDogRepository;

    @Mock
    DogRepository dogRepository;

    @InjectMocks
    AdopterDogService adopterDogService;

    private final AdopterDog expected = new AdopterDog();
    private final AdopterDog expected1 = new AdopterDog();

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
        expected.setDog(new Dog(1L, "Шарик", "Лабрадор", 2021, "Черно-белый"));

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
     * Method Testing <b>add()</b> в AdopterDogService
     * <br>
     * Mockito: when the method is called <b>AdopterDogRepository::save</b>,
     * dog adopter returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking the addition of a new adopter og and saving it in the database")
    public void addAdopterDogTest() {
        when(adopterDogRepository.save(any(AdopterDog.class))).thenReturn(expected);

        AdopterDog actual = adopterDogService.add(expected);

        Assertions.assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        Assertions.assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        Assertions.assertThat(actual.getUserName()).isEqualTo(expected.getUserName());
        Assertions.assertThat(actual.getAge()).isEqualTo(expected.getAge());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(actual.getTelephoneNumber()).isEqualTo(expected.getTelephoneNumber());
        Assertions.assertThat(actual.getState()).isEqualTo(expected.getState());
    }

    /**
     * Method Testing <b>get()</b> в AdopterDogService
     * <br>
     * Mockito: when the method is called <b>AdopterDogRepository::findById</b>,
     * dog adopter returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking the search for the dog's adopter by id and returning it from the database")
    public void getByIdAdopterDogTest() {
        when(adopterDogRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));

        AdopterDog actual = adopterDogService.get(expected.getChatId());

        Assertions.assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        Assertions.assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        Assertions.assertThat(actual.getUserName()).isEqualTo(expected.getUserName());
        Assertions.assertThat(actual.getAge()).isEqualTo(expected.getAge());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(actual.getTelephoneNumber()).isEqualTo(expected.getTelephoneNumber());
        Assertions.assertThat(actual.getState()).isEqualTo(expected.getState());
    }

    /**
     * Test for throwing an exception in a method <b>get()</b> в AdopterDogService
     * <br>
     * Mockito: when the method is called <b>AdopterDogRepository::findById</b>,
     * an exception is thrown <b>AdopterDogNotFoundException</b>
     *
     * @throws AdopterDogNotFoundException
     */
    @Test
    @DisplayName("Checking for an Exception Throw in the Dog Adopter Finder Method")
    public void getByIdAdopterDogExceptionTest() {
        when(adopterDogRepository.findById(any(Long.class))).thenThrow(AdopterDogNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(AdopterDogNotFoundException.class,
                () -> adopterDogService.get(0L));
    }

    /**
     * Method Testing <b>update()</b> в AdopterDogService
     * <br>
     * Mockito: when the method is called <b>AdopterDogRepository::findById</b> и <b>AdopterDogRepository::save</b>,
     * edited dog adopter returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking the editing of the dog's adopter, saving and returning it from the database")
    public void updateAdopterDogTest() {
        when(adopterDogRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));
        when(adopterDogRepository.save(any(AdopterDog.class))).thenReturn(expected);
        when(dogRepository.findById(any(Long.class))).thenReturn(Optional.of(expected.getDog()));

        AdopterDog actual = adopterDogService.update(expected);

        Assertions.assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        Assertions.assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        Assertions.assertThat(actual.getUserName()).isEqualTo(expected.getUserName());
        Assertions.assertThat(actual.getAge()).isEqualTo(expected.getAge());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(actual.getTelephoneNumber()).isEqualTo(expected.getTelephoneNumber());
        Assertions.assertThat(actual.getState()).isEqualTo(expected.getState());
    }

    /**
     * Test for throwing an exception in a method <b>update()</b> в AdopterDogService
     * <br>
     * Mockito: when the method is called <b>AdopterDogRepository::findById</b>,
     * an exception is thrown <b>AdopterDogNotFoundException</b>
     *
     * @throws AdopterDogNotFoundException
     */
    @Test
    @DisplayName("Checking for an exception being thrown in the dog's adopter edit method")
    public void updateAdopterDogExceptionTest() {
        when(adopterDogRepository.findById(any(Long.class))).thenThrow(AdopterDogNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(AdopterDogNotFoundException.class,
                () -> adopterDogService.update(expected));
    }

    /**
     * Method Testing <b>getAll()</b> в AdopterDogService
     * <br>
     * Mockito: when the method is called <b>AdopterDogRepository::findAll</b>,
     * dog adopters collection returns <b>adopterDogs</b>
     */
    @Test
    @DisplayName("Verification of finding all adopters of dogs and returning them from the database")
    public void getAllAdopterDogsTest() {
        List<AdopterDog> adopterDogs = new ArrayList<>();
        adopterDogs.add(expected);
        adopterDogs.add(expected1);

        when(adopterDogRepository.findAll()).thenReturn(adopterDogs);

        Collection<AdopterDog> actual = adopterDogService.getAll();

        Assertions.assertThat(actual.size()).isEqualTo(adopterDogs.size());
        Assertions.assertThat(actual).isEqualTo(adopterDogs);
    }

    /**
     * Method Testing <b>getAll()</b> в AdopterDogService
     * <br>
     * Mockito: when the method is called <b>AdopterDogRepository::findAll</b>,
     * empty dog adopter collection returns <b>adopterDogs</b>
     */
    @Test
    @DisplayName("Checking to find all dog adopters and return an empty list from the database")
    public void getAllAdopterDogsTestReturnsEmpty() {
        List<AdopterDog> adopterDogs = new ArrayList<>();
        when(adopterDogRepository.findAll()).thenReturn(adopterDogs);
        assertThat(adopterDogService.getAll()).isEqualTo(adopterDogs);
    }

    /**
     * Method Testing <b>update()</b> в AdopterDogService
     * <br>
     * Mockito: when the method is called <b>AdopterDogRepository::findById</b> и <b>AdopterDogRepository::save</b>,
     * edited adopter returns with dog <b>expected</b>
     */
    @Test
    @DisplayName("Checking if a dog is added as a field of the adopter object")
    public void updateFieldDogIdByAdopterCat() {
        when(adopterDogRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));
        when(adopterDogRepository.save(any(AdopterDog.class))).thenReturn(expected);
        when(dogRepository.findById(any(Long.class))).thenReturn(Optional.of(expected.getDog()));

        AdopterDog actual = adopterDogService.update(expected);

        Assertions.assertThat(actual.getDog()).isEqualTo(expected.getDog());
    }

    /**
     * Method Testing <b>update()</b> в AdopterDogService
     * <br>
     * Mockito: when the method is called <b>AdopterDogRepository::findById</b> и <b>AdopterDogRepository::save</b>,
     * edited adopter returns with collection of dog reports <b>reportDogs</b>
     */
    @Test
    @DisplayName("Validate adding a list of reports as a field of the adopter object")
    public void updateFieldReportAdopterDog() {
        ReportDog expectedTest1 = new ReportDog();
        expectedTest1.setId(1L);
        expectedTest1.setReportDate(LocalDate.of(2023, 3, 24));
        expectedTest1.setFileId("Test124578");
        expectedTest1.setCaption("Рацион: гуд; Самочувствие: гуд; Поведение: гуд");
        expectedTest1.setExamination(ExaminationStatus.UNCHECKED);

        ReportDog expectedTest2 = new ReportDog();
        expectedTest2.setId(2L);
        expectedTest2.setReportDate(LocalDate.of(2023, 3, 24));
        expectedTest2.setFileId("Test986532");
        expectedTest2.setCaption("Рацион: гуд; Самочувствие: гуд; Поведение: гуд");
        expectedTest2.setExamination(ExaminationStatus.ACCEPTED);

        List<ReportDog> reportDogs = new ArrayList<>();
        reportDogs.add(expectedTest1);
        reportDogs.add(expectedTest2);

        expected.setReports(reportDogs);

        when(adopterDogRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));
        when(adopterDogRepository.save(any(AdopterDog.class))).thenReturn(expected);
        when(dogRepository.findById(any(Long.class))).thenReturn(Optional.of(expected.getDog()));

        AdopterDog actual = adopterDogService.update(expected);

        Assertions.assertThat(actual.getChatId()).isEqualTo(expected.getChatId());
        Assertions.assertThat(actual.getReports()).isEqualTo(expected.getReports());
    }
}
