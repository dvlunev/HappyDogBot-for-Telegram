package com.team4.happydogbot.service;

import com.team4.happydogbot.entity.ReportDog;
import com.team4.happydogbot.exception.ReportDogNotFoundException;
import com.team4.happydogbot.repository.ReportDogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;

/**
 * Service class containing a set of CRUD operations on the ReportDog object
 *
 * @see ReportDog
 * @see ReportDogRepository
 */
@Slf4j
@Service
@Transactional
public class ReportDogService {
    private final ReportDogRepository reportDogRepository;
    private final Bot bot;

    public ReportDogService(ReportDogRepository reportRepository, Bot bot) {
        this.reportDogRepository = reportRepository;
        this.bot = bot;
    }

    /**
     * The method saves the user's report
     *
     * @param reportDog
     * @return {@link ReportDogRepository#save(Object)}
     * @see ReportDogService
     */
    public ReportDog add(ReportDog reportDog) {
        log.info("Was invoked method to add a report");

        return this.reportDogRepository.save(reportDog);
    }

    /**
     * The method finds and returns a report by id
     *
     * @param id
     * @return {@link ReportDogRepository#findById(Object)}
     * @throws ReportDogNotFoundException if the report with the specified id is not found
     * @see ReportDogService
     */
    public ReportDog get(Long id) {
        log.info("Was invoked method to get a report by id={}", id);

        return this.reportDogRepository.findById(id)
                .orElseThrow(ReportDogNotFoundException::new);
    }

    /**
     * The method finds and deletes a report by id
     *
     * @param id
     * @return true if the removal was successful
     * @throws ReportDogNotFoundException if the report with the specified id is not found
     * @see ReportDogService
     */
    public boolean remove(Long id) {
        log.info("Was invoked method to remove a report by id={}", id);

        if (reportDogRepository.existsById(id)) {
            reportDogRepository.deleteById(id);
            return true;
        }
        throw new ReportDogNotFoundException();
    }

    /**
     * The method updates and returns the report
     *
     * @param reportDog
     * @return {@link ReportDogRepository#save(Object)}
     * @throws ReportDogNotFoundException if the report with the specified id is not found
     * @see ReportDogService
     */
    public ReportDog update(ReportDog reportDog) {
        log.info("Was invoked method to upload a reportDog");

        if (reportDog.getId() != null && get(reportDog.getId()) != null) {
            ReportDog findDog = get(reportDog.getId());
            findDog.setReportDate(reportDog.getReportDate());
            findDog.setFileId(reportDog.getFileId());
            findDog.setCaption(reportDog.getCaption());
            findDog.setExamination(reportDog.getExamination());
            return this.reportDogRepository.save(findDog);
        }
        throw new ReportDogNotFoundException();
    }

    /**
     * The method finds and returns all reports
     *
     * @return {@link ReportDogRepository#findAll()}
     * @see ReportDogService
     */
    public Collection<ReportDog> getAll() {
        log.info("Was invoked method to get all reportsDog");

        return this.reportDogRepository.findAll();
    }

    /**
     * The method finds the fileId of the photo for the report in the database, makes a Telegram request to receive the photo file,
     * receives a photo, reads and returns a byte of the photo
     *
     * @param id
     * @return byte of the photo
     */
    public byte[] getFile(Long id) {
        log.info("Was invoked method to get a photo of the report by id={}", id);

        String fileId = reportDogRepository.getReferenceById(id).getFileId();
        try {
            File file = bot.execute(GetFile.builder().fileId(fileId).build());
            java.io.File file1 = bot.downloadFile(file);
            return Files.readAllBytes(file1.toPath());
        } catch (TelegramApiException | IOException e) {
            throw new ReportDogNotFoundException();
        }
    }
}
