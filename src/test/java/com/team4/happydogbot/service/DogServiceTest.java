package com.team4.happydogbot.service;

import com.team4.happydogbot.entity.Dog;
import com.team4.happydogbot.exception.DogNotFoundException;
import com.team4.happydogbot.repository.DogRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Test class to test CRUD operations in dog service class
 *
 * @see Dog
 * @see DogRepository
 * @see DogService
 * @see DogServiceTest
 */
@ExtendWith(MockitoExtension.class)
public class DogServiceTest {

    @Mock
    DogRepository dogRepository;

    @InjectMocks
    DogService dogService;

    private final Dog expected = new Dog();
    private final Dog expected1 = new Dog();

    @BeforeEach
    public void setUp() {
        expected.setId(1L);
        expected.setName("Bublik");
        expected.setBreed("Pudel");
        expected.setYearOfBirth(2020);
        expected.setDescription("Test");

        expected1.setId(2L);
        expected1.setName("Korjik");
        expected1.setBreed("Buldog");
        expected1.setYearOfBirth(2017);
        expected1.setDescription("Test");
    }

    /**
     * Method Testing <b>add()</b> in DogService
     * <br>
     * Mockito: when the method is called <b>DogRepository::save</b>,
     * the dog returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking for adding a new dog and saving it to the database")
    public void addDogTest() {
        when(dogRepository.save(any(Dog.class))).thenReturn(expected);

        Dog actual = dogService.add(expected);

        Assertions.assertThat(actual.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(actual.getBreed()).isEqualTo(expected.getBreed());
        Assertions.assertThat(actual.getYearOfBirth()).isEqualTo(expected.getYearOfBirth());
        Assertions.assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
    }

    /**
     * Method Testing <b>get()</b> in DogService
     * <br>
     * Mockito: when the method is called <b>DogRepository::findById</b>,
     * the dog returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking the search for a dog by id and returning it from the database")
    public void getByIdDogTest() {
        when(dogRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));

        Dog actual = dogService.get(expected.getId());

        Assertions.assertThat(actual.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(actual.getBreed()).isEqualTo(expected.getBreed());
        Assertions.assertThat(actual.getYearOfBirth()).isEqualTo(expected.getYearOfBirth());
        Assertions.assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
    }

    /**
     * Test for throwing an exception in a method <b>get()</b> in DogService
     * <br>
     * Mockito: when the method is called <b>DogRepository::findById</b>,
     * an exception is thrown <b>DogNotFoundException</b>
     *
     * @throws DogNotFoundException
     */
    @Test
    @DisplayName("Checking for an exception throw in the dog finder method")
    public void getByIdDogExceptionTest() {
        when(dogRepository.findById(any(Long.class))).thenThrow(DogNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(DogNotFoundException.class, () -> dogService.get(0L));
    }

    /**
     * Method Testing <b>update()</b> in DogService
     * <br>
     * Mockito: when the method is called <b>DogRepository::findById</b> и <b>DogRepository::save</b>,
     * the edited dog returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking cat editing, saving and returning it from the database")
    public void updateDogTest() {
        when(dogRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));
        when(dogRepository.save(any(Dog.class))).thenReturn(expected);

        Dog actual = dogService.update(expected);

        Assertions.assertThat(actual.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(actual.getBreed()).isEqualTo(expected.getBreed());
        Assertions.assertThat(actual.getYearOfBirth()).isEqualTo(expected.getYearOfBirth());
        Assertions.assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
    }

    /**
     * Test for throwing an exception in a method <b>update()</b> in DogService
     * <br>
     * Mockito: when the method is called <b>DogRepository::findById</b>,
     * an exception is thrown <b>DogNotFoundException</b>
     *
     * @throws DogNotFoundException
     */
    @Test
    @DisplayName("Checking for an exception throw in the dog edit method")
    public void updateDogExceptionTest() {
        when(dogRepository.findById(any(Long.class))).thenThrow(DogNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(DogNotFoundException.class,
                () -> dogService.update(expected));
    }

    /**
     * Method Testing <b>getAll()</b> in DogService
     * <br>
     * Mockito: when the method is called <b>DogRepository::findAll</b>,
     * the dog collection returns <b>adopterDogs</b>
     */
    @Test
    @DisplayName("Checking to find all dogs and return them from the database")
    public void getAllDogsTest() {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(expected);
        dogs.add(expected1);

        when(dogRepository.findAll()).thenReturn(dogs);

        Collection<Dog> actual = dogService.getAll();

        Assertions.assertThat(actual.size()).isEqualTo(dogs.size());
        Assertions.assertThat(actual).isEqualTo(dogs);
    }

    /**
     * Method Testing <b>getAll()</b> in DogService
     * <br>
     * Mockito: when the method is called <b>DogRepository::findAll</b>,
     * empty dog adopter collection returns <b>adopterDogs</b>
     */
    @Test
    @DisplayName("Checking to find all dogs and return an empty list from the database")
    public void getAllDogsTestReturnsEmpty() {
        List<Dog> dogs = new ArrayList<>();
        when(dogRepository.findAll()).thenReturn(dogs);
        assertThat(dogService.getAll()).isEqualTo(dogs);
    }
}
