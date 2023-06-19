package com.team4.happydogbot.constants;

/**
 * The class contains constants with commands that the bot can execute
 */
public class BotCommands {

    // Commands stage 0. The entry point of communication between the bot and the user
    public static final String START_CMD = "/start";
    public static final String SHELTER_CAT = "Приют для кошек";
    public static final String SHELTER_DOG = "Приют для собак";
    public static final String SHELTER_CHOOSE = "Вернуться к выбору приюта";

    public static final String SHELTER_INFO_CMD = "Узнать информацию о приюте";
    public static final String PET_INFO_CMD = "Узнать как взять животное из приюта";
    public static final String SEND_REPORT_CMD = "Прислать отчет о питомце";
    public static final String CALL_VOLUNTEER_CMD = "Позвать волонтера";
    public static final String FINISH_VOLUNTEER_CMD = "Закончить разговор с волонтером";

    // Commands stage 1. Introductory information about the shelter: where it is located, how and when it works,
    // what are the rules for admission to the territory of the shelter, the rules for staying inside and communicating with dogs.
    public static final String SHELTER_ABOUT_CMD = "Общая информация о приюте";
    public static final String SHELTER_SCHEDULE_ADDRESS_CMD = "Расписание, адрес";
    public static final String SHELTER_SAFETY_CMD = "Техника безопасности";
    public static final String CAR_PASS_CMD = "Пропуск на машину";
    public static final String SEND_CONTACT_CMD = "Отправить свои контакты";
    public static final String BACK_CMD = "Назад";

    public static final String[] KEYBOARD_SHELTER_ABOUT = {
            SHELTER_ABOUT_CMD,
            SHELTER_SCHEDULE_ADDRESS_CMD,
            SHELTER_SAFETY_CMD,
            CAR_PASS_CMD,
            SEND_CONTACT_CMD,
            CALL_VOLUNTEER_CMD
    };

    // Commands stage 2. Helping potential adopters of a dog from a shelter
    // with bureaucratic (how to draw up a contract) and everyday (how to prepare for life with a dog) questions.
    public static final String PET_RULES_CMD = "Правила знакомства с животным";
    public static final String PET_DOCS_CMD = "Необходимые документы";
    public static final String PET_TRANSPORT_CMD = "Рекомендации по транспортировке";
    public static final String PET_HOUSE_CMD = "Обустройство дома для животного";
    public static final String PET_ADVICES_CMD = "Первичное общение с собакой";
    public static final String PET_CYNOLOGISTS_CMD = "Проверенные кинологи";
    public static final String PET_REFUSAL_CMD = "Причины отказа";

    public static final String[] KEYBOARD_DOG_ADOPT = {
            PET_RULES_CMD,
            PET_DOCS_CMD,
            PET_TRANSPORT_CMD,
            PET_HOUSE_CMD,
            PET_ADVICES_CMD,
            PET_CYNOLOGISTS_CMD,
            PET_REFUSAL_CMD,
            SEND_CONTACT_CMD,
            CALL_VOLUNTEER_CMD
    };

    public static final String[] KEYBOARD_CAT_ADOPT = {
            PET_RULES_CMD,
            PET_DOCS_CMD,
            PET_TRANSPORT_CMD,
            PET_HOUSE_CMD,
            PET_REFUSAL_CMD,
            SEND_CONTACT_CMD,
            CALL_VOLUNTEER_CMD
    };

    public static final String PET_HOUSE_FOR_PUPPY_CMD = "Для щенка";
    public static final String PET_HOUSE_FOR_KITTY_CMD = "Для котёнка";
    public static final String PET_HOUSE_FOR_ADULT_CMD = "Для взрослого животного";
    public static final String PET_HOUSE_FOR_SICK_CMD = "C ограниченными возможностями";

    public static final String[] KEYBOARD_DOG_HOUSE = {
            PET_HOUSE_FOR_PUPPY_CMD,
            PET_HOUSE_FOR_ADULT_CMD,
            PET_HOUSE_FOR_SICK_CMD
    };

    public static final String[] KEYBOARD_CAT_HOUSE = {
            PET_HOUSE_FOR_KITTY_CMD,
            PET_HOUSE_FOR_ADULT_CMD,
            PET_HOUSE_FOR_SICK_CMD
    };


    // Commands stage 3. Keeping a Pet
    public static final String REPORT_FORM = "Форма ежедневного отчета";
    public static final String REPORT_EXAMPLE = "Образец отчета";
    public static final String SEND_REPORT = "Отправить отчет";

    public static final String SEND_ATTENTION = "”Дорогой усыновитель, мы заметили, что ты заполняешь " +
            "отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее к этому занятию. " +
            "В противном случае, волонтеры приюта будут обязаны самолично проверять условия содержания собаки”";

    public static final String[] KEYBOARD_REPORT = {
            REPORT_FORM,
            REPORT_EXAMPLE,
            SEND_REPORT,
            CALL_VOLUNTEER_CMD
    };

    // Trial Decisions

    public static final String TAKE_DECISION = "Примите решение по итогу прохождения испытательного срока у пользователя: ";
    public static final String FINISH_PROBATION = "Испытательный срок пройден";
    public static final String EXTEND_PROBATION_14 = "Продлить на 14 дней";
    public static final String EXTEND_PROBATION_30 = "Продлить на 30 дней";
    public static final String REFUSE = "Отказать в усыновлении";


    public static final String[] KEYBOARD_DECISION = {
            FINISH_PROBATION,
            EXTEND_PROBATION_14,
            EXTEND_PROBATION_30,
            REFUSE
    };


}
