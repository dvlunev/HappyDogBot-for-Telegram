package com.team4.happydogbot.repository;

import com.team4.happydogbot.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface containing methods for working with the animal database
 *
 * @see Dog
 * @see com.team4.happydogbot.service.DogService
 */
@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {

}
