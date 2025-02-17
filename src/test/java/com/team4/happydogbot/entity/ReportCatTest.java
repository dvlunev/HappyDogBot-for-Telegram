package com.team4.happydogbot.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 * Test class to test the creation of a cat report
 *
 * @see ReportCat
 * @see ReportCatTest
 */
public class ReportCatTest {

    private final Long id = 1L;
    private final LocalDate reportDate = LocalDate.of(2023, 3, 24);
    private final String fileId = "Test124578";
    private final String caption = "Рацион: гуд; Самочувствие: гуд; Поведение: гуд";
    private final ExaminationStatus examinationStatus = ExaminationStatus.UNCHECKED;
    ReportCat reportCat = new ReportCat(id, reportDate, fileId, caption, examinationStatus);

    @Test
    @DisplayName("Checking for data when creating a cat report")
    public void createReportCatTest() {
        Long reportId = reportCat.getId();
        LocalDate reportDateCat = reportCat.getReportDate();
        String reportFileId = reportCat.getFileId();
        String reportCaption = reportCat.getCaption();
        ExaminationStatus reportExamination = reportCat.getExamination();

        Assertions.assertEquals(id, reportId);
        Assertions.assertEquals(reportDate, reportDateCat);
        Assertions.assertEquals(fileId, reportFileId);
        Assertions.assertEquals(caption, reportCaption);
        Assertions.assertEquals(examinationStatus, reportExamination);
    }

    @Test
    @DisplayName("Checking for the absence of transmitted data when creating a cat report")
    public void createReportCatNullTest() {
        ReportCat reportCatTest = new ReportCat();
        Long reportId = reportCatTest.getId();
        LocalDate reportDateCat = reportCatTest.getReportDate();
        String reportFileId = reportCatTest.getFileId();
        String reportCaption = reportCatTest.getCaption();
        ExaminationStatus reportExamination = reportCatTest.getExamination();

        Assertions.assertNull(reportId);
        Assertions.assertNull(reportDateCat);
        Assertions.assertNull(reportFileId);
        Assertions.assertNull(reportCaption);
        Assertions.assertEquals(reportExamination, ExaminationStatus.UNCHECKED);
    }
}
