package com.team4.happydogbot.entity;

/**
 * Класс, содержащий параметры для всех стадий пользовательского состояния
 * @see AdopterCat
 * @see
 */
public enum Status {

    USER("Пользователь общается с ботом"),//возможно в этом статусе нет необходимости!
    REGISTRATION("Пользователь оставил свои контактные данные"),
    ADOPTION_DENIED("Потенциальному родителю отказано в усыновлении животного"),
    PROBATION("Назначен испытательный срок - 30 календарных дней"),
    ADDITIONAL_PERIOD("Назначен дополнительный испытательный срок - 14 календарных дней"),
    FINISHED_PROBATION_PERIOD("Испытательный срок пройден - работа с усыновителем закончена");

    private final String status;

    Status(String status) {
        this.status = status;
    }
}
