package com.team4.happydogbot.repository;

import com.team4.happydogbot.entity.ReportDog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface containing methods for working with the reporting database
 *
 * @see ReportDog
 * @see com.team4.happydogbot.service.ReportDogService
 */
@Repository
public interface ReportDogRepository extends JpaRepository<ReportDog, Long> {

}
