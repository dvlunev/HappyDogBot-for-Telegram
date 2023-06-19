package com.team4.happydogbot.service;

import com.team4.happydogbot.entity.AdopterDog;
import com.team4.happydogbot.exception.AdopterDogNotFoundException;
import com.team4.happydogbot.repository.AdopterDogRepository;
import com.team4.happydogbot.repository.DogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service class containing a set of CRUD operations on the AdopterDog object
 *
 * @see AdopterDog
 * @see AdopterDogRepository
 */
@Slf4j
@Service
public class AdopterDogService {
    private final AdopterDogRepository adopterDogRepository;

    private final DogRepository dogRepository;

    public AdopterDogService(AdopterDogRepository adopterDogRepository, DogRepository dogRepository) {
        this.adopterDogRepository = adopterDogRepository;
        this.dogRepository = dogRepository;
    }

    /**
     * The method creates a new user
     *
     * @param adopterDog
     * @return {@link AdopterDogRepository#save(Object)}
     * @see AdopterDogService
     */
    public AdopterDog add(AdopterDog adopterDog) {
        log.info("Was invoked method to create a adopterDog");

        return this.adopterDogRepository.save(adopterDog);
    }

    /**
     * The method finds and returns the user by id
     *
     * @param id
     * @return {@link AdopterDogRepository#findById(Object)}
     * @throws AdopterDogNotFoundException if the user with the specified id is not found
     * @see AdopterDogService
     */
    public AdopterDog get(Long id) {
        log.info("Was invoked method to get a adopterDog by id={}", id);

        return this.adopterDogRepository.findById(id)
                .orElseThrow(AdopterDogNotFoundException::new);
    }

    /**
     * The method finds and deletes a user by id
     *
     * @param id
     * @return true if the removal was successful
     * @throws AdopterDogNotFoundException if the user with the specified id is not found
     */
    public boolean remove(Long id) {
        log.info("Was invoked method to remove a adopterDog by id={}", id);

        if (adopterDogRepository.existsById(id)) {
            adopterDogRepository.deleteById(id);
            return true;
        }
        throw new AdopterDogNotFoundException();
    }

    /**
     * The method updates and returns the user
     *
     * @param adopterDog
     * @return {@link AdopterDogRepository#save(Object)}
     * @throws AdopterDogNotFoundException if the user with the specified id is not found
     * @see AdopterDogService
     */
    public AdopterDog update(AdopterDog adopterDog) {
        log.info("Was invoked method to update a adopterDog");

        if (adopterDog.getChatId() != null && get(adopterDog.getChatId()) != null) {
            AdopterDog findAdopterDog = get(adopterDog.getChatId());
            findAdopterDog.setFirstName(adopterDog.getFirstName());
            findAdopterDog.setLastName(adopterDog.getLastName());
            findAdopterDog.setUserName(adopterDog.getUserName());
            findAdopterDog.setAge(adopterDog.getAge());
            findAdopterDog.setAddress(adopterDog.getAddress());
            findAdopterDog.setTelephoneNumber(adopterDog.getTelephoneNumber());
            findAdopterDog.setState(adopterDog.getState());
            findAdopterDog.setStatusDate(adopterDog.getStatusDate());
            findAdopterDog.setDog(dogRepository.findById(adopterDog.getDog().getId()).get());
            return this.adopterDogRepository.save(findAdopterDog);
        }
        throw new AdopterDogNotFoundException();
    }

    /**
     * The method finds all users
     *
     * @return {@link AdopterDogRepository#findById(Object)}
     * @see AdopterDogService
     */
    public Collection<AdopterDog> getAll() {
        log.info("Was invoked method to get all adoptersDod");

        return this.adopterDogRepository.findAll();
    }
}

