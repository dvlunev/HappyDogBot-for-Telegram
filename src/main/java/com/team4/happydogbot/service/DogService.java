package com.team4.happydogbot.service;

import com.team4.happydogbot.entity.Dog;
import com.team4.happydogbot.exception.DogNotFoundException;
import com.team4.happydogbot.repository.DogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service class containing a set of CRUD operations on the Dog object
 *
 * @see Dog
 * @see DogRepository
 */
@Slf4j
@Service
public class DogService {

    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    /**
     * The method creates a new dog
     *
     * @param dog
     * @return {@link DogRepository#save(Object)}
     * @see DogService
     */
    public Dog add(Dog dog) {
        log.info("Was invoked method to create a dog");

        return this.dogRepository.save(dog);
    }

    /**
     * The method finds and returns a dog by id
     *
     * @param id
     * @return {@link DogRepository#findById(Object)}
     * @throws DogNotFoundException if the dog with the specified id is not found
     * @see DogService
     */
    public Dog get(Long id) {
        log.info("Was invoked method to get a dog by id={}", id);

        return this.dogRepository.findById(id)
                .orElseThrow(DogNotFoundException::new);
    }

    /**
     * The method finds and removes a dog by id
     *
     * @param id
     * @return true if the removal was successful
     * @throws DogNotFoundException if the dog with the specified id is not found
     */
    public boolean remove(Long id) {
        log.info("Was invoked method to remove a dog by id={}", id);

        if (dogRepository.existsById(id)) {
            if (dogRepository.getReferenceById(id).getAdopterDog() != null) {
                dogRepository.getReferenceById(id).getAdopterDog().setDog(null);
            }
            dogRepository.deleteById(id);
            return true;
        }
        throw new DogNotFoundException();
    }

    /**
     * The method updates and returns the dog
     *
     * @param dog
     * @return {@link DogRepository#save(Object)}
     * @throws DogNotFoundException if the dog with the specified id is not found
     * @see DogService
     */
    public Dog update(Dog dog) {
        log.info("Was invoked method to update a dog");

        if (dog.getId() != null && get(dog.getId()) != null) {
            Dog findDog = get(dog.getId());
            findDog.setName(dog.getName());
            findDog.setBreed(dog.getBreed());
            findDog.setYearOfBirth(dog.getYearOfBirth());
            findDog.setDescription(dog.getDescription());
            return this.dogRepository.save(findDog);
        }
        throw new DogNotFoundException();
    }

    /**
     * The method finds and returns all dogs
     *
     * @return {@link DogRepository#findById(Object)}
     * @see DogService
     */
    public Collection<Dog> getAll() {
        log.info("Was invoked method to get all dogs");

        return this.dogRepository.findAll();
    }
}
