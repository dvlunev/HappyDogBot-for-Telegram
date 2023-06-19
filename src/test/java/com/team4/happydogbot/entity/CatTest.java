package com.team4.happydogbot.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class to test a cat creation
 * @see Cat
 * @see CatTest
 */
public class CatTest {
    private final Long id = 1L;
    private final String name = "Ponchik";
    private final String breed = "Bolinez";
    private final int yearOfBirth = 2020;
    private final String description = "Test1";

    Cat catTest = new Cat(id, name, breed, yearOfBirth, description);

    @Test
    @DisplayName("Checking for data when creating a cat")
    public void createCatTest() {
        Long catId = catTest.getId();
        String catName = catTest.getName();
        String catBreed = catTest.getBreed();
        int catYearOfBirth = catTest.getYearOfBirth();
        String catDescription = catTest.getDescription();

        Assertions.assertEquals(id, catId);
        Assertions.assertEquals(name, catName);
        Assertions.assertEquals(breed, catBreed);
        Assertions.assertEquals(yearOfBirth, catYearOfBirth);
        Assertions.assertEquals(description, catDescription);
    }

    @Test
    @DisplayName("Checking for the absence of transmitted data when creating a cat")
    public void createCatNullTest() {
        Cat catTest = new Cat();
        Long catId = catTest.getId();
        String catName = catTest.getName();
        String catBreed = catTest.getBreed();
        int catYearOfBirth = catTest.getYearOfBirth();
        int catYearActual = 0;
        String catDescription = catTest.getDescription();

        Assertions.assertNull(catId);
        Assertions.assertNull(catName);
        Assertions.assertNull(catBreed);
        Assertions.assertEquals(catYearOfBirth, catYearActual);
        Assertions.assertNull(catDescription);
    }
}
