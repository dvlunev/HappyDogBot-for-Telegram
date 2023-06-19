package com.team4.happydogbot.service;

import com.team4.happydogbot.entity.AdopterCat;
import com.team4.happydogbot.exception.AdopterCatNotFoundException;
import com.team4.happydogbot.repository.AdopterCatRepository;
import com.team4.happydogbot.repository.CatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service class containing a set of CRUD operations on the AdopterCat object
 *
 * @see AdopterCat
 * @see AdopterCatRepository
 */
@Slf4j
@Service
public class AdopterCatService {

    private final AdopterCatRepository adopterCatRepository;

    private final CatRepository catRepository;

    public AdopterCatService(AdopterCatRepository adopterCatRepository, CatRepository catRepository) {
        this.adopterCatRepository = adopterCatRepository;
        this.catRepository = catRepository;
    }

    /**
     * The method creates a new user
     *
     * @param adopterCat
     * @return {@link AdopterCatRepository#save(Object)}
     * @see AdopterCatService
     */
    public AdopterCat add(AdopterCat adopterCat) {
        log.info("Was invoked method to add a adopterCat");
        return this.adopterCatRepository.save(adopterCat);
    }

    /**
     * The method finds and returns the user by id
     *
     * @param id
     * @return {@link AdopterCatRepository#findById(Object)}
     * @throws AdopterCatNotFoundException if the user with the specified id is not found
     * @see AdopterCatService
     */
    public AdopterCat get(Long id) {
        log.info("Was invoked method to get a adopterCat by id={}", id);
        return this.adopterCatRepository.findById(id)
                .orElseThrow(AdopterCatNotFoundException::new);
    }

    /**
     * The method finds and deletes a user by id
     *
     * @param id
     * @throws AdopterCatNotFoundException if the user with the specified id is not found
     * @see AdopterCatService
     */
    public boolean remove(Long id) {
        log.info("Was invoked method to remove a adopterCat by id={}", id);
        if (adopterCatRepository.existsById(id)) {
            adopterCatRepository.deleteById(id);
            return true;
        }
        throw new AdopterCatNotFoundException();
    }

    /**
     * The method updates and returns the user
     *
     * @param adopterCat
     * @return {@link AdopterCatRepository#save(Object)}
     * @throws AdopterCatNotFoundException if the user with the specified id is not found
     * @see AdopterCatService
     */
    public AdopterCat update(AdopterCat adopterCat) {
        log.info("Was invoked method to update a adopterCat");

        if (adopterCat.getChatId() != null && get(adopterCat.getChatId()) != null) {
            AdopterCat findAdopterCat = get(adopterCat.getChatId());
            findAdopterCat.setFirstName(adopterCat.getFirstName());
            findAdopterCat.setLastName(adopterCat.getLastName());
            findAdopterCat.setUserName(adopterCat.getUserName());
            findAdopterCat.setAge(adopterCat.getAge());
            findAdopterCat.setAddress(adopterCat.getAddress());
            findAdopterCat.setTelephoneNumber(adopterCat.getTelephoneNumber());
            findAdopterCat.setState(adopterCat.getState());
            findAdopterCat.setStatusDate(adopterCat.getStatusDate());
            findAdopterCat.setCat(catRepository.findById(adopterCat.getCat().getId()).get());
            return this.adopterCatRepository.save(findAdopterCat);
        }
        throw new AdopterCatNotFoundException();
    }

    /**
     * The method finds all users
     *
     * @return {@link AdopterCatRepository#findById(Object)}
     * @see AdopterCatService
     */
    public Collection<AdopterCat> getAll() {
        log.info("Was invoked method to get all adoptersCat");
        return this.adopterCatRepository.findAll();
    }
}
