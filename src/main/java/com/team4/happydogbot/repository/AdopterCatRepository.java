package com.team4.happydogbot.repository;

import com.team4.happydogbot.entity.AdopterCat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface containing methods for working with the database of adopters
 *
 * @see AdopterCat
 * @see com.team4.happydogbot.service.AdopterCatService
 */
@Repository
public interface AdopterCatRepository extends JpaRepository<AdopterCat, Long> {
    AdopterCat findAdopterCatByChatId(Long chatId);
}
