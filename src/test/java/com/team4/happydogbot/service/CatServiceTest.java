package com.team4.happydogbot.service;

import com.team4.happydogbot.entity.Cat;
import com.team4.happydogbot.exception.CatNotFoundException;
import com.team4.happydogbot.repository.CatRepository;
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
 * Test class to test CRUD operations in cat service class
 *
 * @see Cat
 * @see CatRepository
 * @see CatService
 * @see CatServiceTest
 */
@ExtendWith(MockitoExtension.class)
public class CatServiceTest {

    @Mock
    CatRepository catRepository;

    @InjectMocks
    CatService catService;

    private final Cat expected = new Cat();
    private final Cat expected1 = new Cat();

    @BeforeEach
    public void setUp() {
        expected.setId(1L);
        expected.setName("Ponchik");
        expected.setBreed("Bolinez");
        expected.setYearOfBirth(2020);
        expected.setDescription("Test");

        expected1.setId(2L);
        expected1.setName("Bublik");
        expected1.setBreed("Siam");
        expected1.setYearOfBirth(2017);
        expected1.setDescription("Test");
    }

    /**
     * Method Testing <b>add()</b> in CatService
     * <br>
     * Mockito: when the method is called <b>CatRepository::save</b>,
     * the cat returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking for adding a new cat and saving it to the database")
    public void addCatTest() {
        when(catRepository.save(any(Cat.class))).thenReturn(expected);

        Cat actual = catService.add(expected);

        Assertions.assertThat(actual.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(actual.getBreed()).isEqualTo(expected.getBreed());
        Assertions.assertThat(actual.getYearOfBirth()).isEqualTo(expected.getYearOfBirth());
        Assertions.assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
    }

    /**
     * Method Testing <b>get()</b> in CatService
     * <br>
     * Mockito: when the method is called <b>CatRepository::findById</b>,
     * the cat returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking the search for a cat by id and returning it from the database")
    public void getByIdCatTest() {
        when(catRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));

        Cat actual = catService.get(expected.getId());

        Assertions.assertThat(actual.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(actual.getBreed()).isEqualTo(expected.getBreed());
        Assertions.assertThat(actual.getYearOfBirth()).isEqualTo(expected.getYearOfBirth());
        Assertions.assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
    }

    /**
     * Test for throwing an exception in a method <b>get()</b> in CatService
     * <br>
     * Mockito: when the method is called <b>CatRepository::findById</b>,
     * an exception is thrown <b>CatNotFoundException</b>
     *
     * @throws CatNotFoundException
     */
    @Test
    @DisplayName("Checking for an exception throw in the cat finder method")
    public void getByIdCatExceptionTest() {
        when(catRepository.findById(any(Long.class))).thenThrow(CatNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(CatNotFoundException.class, () -> catService.get(0L));
    }

    /**
     * Method Testing <b>update()</b> in CatService
     * <br>
     * Mockito: when the method is called <b>CatRepository::findById</b> и <b>CatRepository::save</b>,
     * the edited cat returns <b>expected</b>
     */
    @Test
    @DisplayName("Checking cat editing, saving and returning it from the database")
    public void updateCatTest() {
        when(catRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));
        when(catRepository.save(any(Cat.class))).thenReturn(expected);

        Cat actual = catService.update(expected);

        Assertions.assertThat(actual.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(actual.getBreed()).isEqualTo(expected.getBreed());
        Assertions.assertThat(actual.getYearOfBirth()).isEqualTo(expected.getYearOfBirth());
        Assertions.assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
    }

    /**
     * Test for throwing an exception in a method <b>update()</b> in CatService
     * <br>
     * Mockito: when the method is called <b>CatRepository::findById</b>,
     * an exception is thrown <b>CatNotFoundException</b>
     *
     * @throws CatNotFoundException
     */
    @Test
    @DisplayName("Checking for an exception throw in the cat edit method")
    public void updateCatExceptionTest() {
        when(catRepository.findById(any(Long.class))).thenThrow(CatNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(CatNotFoundException.class,
                () -> catService.update(expected));
    }

    /**
     * Method Testing <b>getAll()</b> in CatService
     * <br>
     * Mockito: when the method is called <b>CatRepository::findAll</b>,
     * the cat collection returns <b>cats</b>
     */
    @Test
    @DisplayName("Checking to find all cats and return them from the database")
    public void getAllCatsTest() {
        List<Cat> cats = new ArrayList<>();
        cats.add(expected);
        cats.add(expected1);

        when(catRepository.findAll()).thenReturn(cats);

        Collection<Cat> actual = catService.getAll();

        Assertions.assertThat(actual.size()).isEqualTo(cats.size());
        Assertions.assertThat(actual).isEqualTo(cats);
    }

    /**
     * Method Testing <b>getAll()</b> in CatService
     * <br>
     * Mockito: when the method is called <b>CatRepository::findAll</b>,
     * empty cat adopter collection returns <b>cats</b>
     */
    @Test
    @DisplayName("Checking to find all cats and return an empty list from the database")
    public void getAllCatsTestReturnsEmpty() {
        List<Cat> cats = new ArrayList<>();
        when(catRepository.findAll()).thenReturn(cats);
        assertThat(catService.getAll()).isEqualTo(cats);
    }
}
