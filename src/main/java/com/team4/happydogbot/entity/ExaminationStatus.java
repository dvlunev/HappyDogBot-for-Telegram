package com.team4.happydogbot.entity;

/**
 * Class containing parameters for all states
 *
 * @see ReportCat
 * @see ReportDog
 */
public enum ExaminationStatus {
    ACCEPTED("Report reviewed and accepted"),
    UNCHECKED("Report not verified"),
    REJECTED("Report reviewed and sent for revision");

    private final String examinationStatus;

    ExaminationStatus(String examinationStatus) {
        this.examinationStatus = examinationStatus;
    }
}
