package com.team4.happydogbot.entity;

/**
 * Class containing parameters for all user state stages
 *
 * @see AdopterCat
 * @see AdopterDog
 */
public enum Status {
    USER("The user has chosen a shelter"),
    REGISTRATION("The user left his contact details"),
    ADOPTION_DENIED("Prospective parent refused to adopt an animal"),
    PROBATION("Probationary period - 30 calendar days"),
    ADDITIONAL_PERIOD_14("An additional probationary period was assigned - 14 calendar days"),
    //исправлено по обновленному ТЗ
    ADDITIONAL_PERIOD_30("An additional probationary period is assigned - 30 calendar days"),
    //дополнено по обновленному ТЗ
    FINISHED_PROBATION_PERIOD("The probationary period has passed - work with the adoptive parent is over");

    private final String status;

    Status(String status) {
        this.status = status;
    }
}
