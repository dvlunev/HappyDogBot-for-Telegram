package com.team4.happydogbot.service;

import com.team4.happydogbot.entity.Cat;
import com.team4.happydogbot.exception.CatNotFoundException;
import com.team4.happydogbot.repository.CatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Collection;

/**
 * Service class containing a set of CRUD operations on a Cat object
 *
 * @see Cat
 * @see CatRepository
 */
@Slf4j
@Service
public class CatService {

    private final CatRepository catRepository;

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    /**
     * The method creates a new cat
     *
     * @param cat
     * @return {@link CatRepository#save(Object)}
     * @see CatService
     */
    public Cat add(Cat cat) {
        log.info("Was invoked method to add a cat");
        return this.catRepository.save(cat);
    }

    /**
     * The method finds and returns a cat by id
     *
     * @param id
     * @return {@link CatRepository#findById(Object)}
     * @throws CatNotFoundException if the cat with the specified id is not found
     * @see CatService
     */
    public Cat get(Long id) {
        log.info("Was invoked method to get a cat by id={}", id);
        return this.catRepository.findById(id)
                .orElseThrow(CatNotFoundException::new);
    }

    /**
     * The method finds and removes a cat by id
     *
     * @param id
     * @throws CatNotFoundException if the cat with the specified id is not found
     * @see CatService
     */
    public boolean remove(Long id) {
        log.info("Was invoked method to remove a cat by id={}", id);
        if (catRepository.existsById(id)) {
            if (catRepository.getReferenceById(id).getAdopterCat() != null) {
                catRepository.getReferenceById(id).getAdopterCat().setCat(null);
            }
            catRepository.deleteById(id);
            return true;
        }
        throw new CatNotFoundException();
    }

    /**
     * The method updates and returns the cat
     *
     * @param cat
     * @return {@link CatRepository#save(Object)}
     * @throws CatNotFoundException if the cat with the specified id is not found
     * @see CatService
     */
    public Cat update(Cat cat) {
        log.info("Was invoked method to update a cat");
        if (cat.getId() != null && get(cat.getId()) != null) {
            Cat findCat = get(cat.getId());
            findCat.setName(cat.getName());
            findCat.setBreed(cat.getBreed());
            findCat.setYearOfBirth(cat.getYearOfBirth());
            findCat.setDescription(cat.getDescription());
            return this.catRepository.save(findCat);
        }
        throw new CatNotFoundException();
    }

    /**
     * The method finds and returns all cats
     *
     * @return {@link CatRepository#findAll()}
     * @see CatService
     */
    public Collection<Cat> getAll() {
        log.info("Was invoked method to get all cats");
        return this.catRepository.findAll();
    }
}
