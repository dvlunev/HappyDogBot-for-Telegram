package com.team4.happydogbot.repository;

import com.team4.happydogbot.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface containing methods for working with the animal database
 *
 * @see Cat
 * @see com.team4.happydogbot.service.CatService
 */
public interface CatRepository extends JpaRepository<Cat, Long> {

}
