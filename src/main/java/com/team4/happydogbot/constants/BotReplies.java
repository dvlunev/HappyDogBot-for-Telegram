package com.team4.happydogbot.constants;


/**
 * The class contains constants with replies that the bot can give
 */
public class BotReplies {

    // Replies stage 0. The entry point of communication between the bot and the user
    public static final String MESSAGE_TEXT_GREETINGS = ", приветствую! Чтобы найти то, что вам нужно - нажмите на кнопку";
    public static final String MESSAGE_TEXT_CHOOSE_SHELTER = "Выберите приют";
    public static final String MESSAGE_TEXT_CHOOSE_ACTION = "Выберите действие";
    public static final String MESSAGE_TEXT_NO_COMMAND = "Нет такой команды";
    public static final String MESSAGE_TEXT_WRITE_VOLUNTEER = "Напишите волонтеру:";
    public static final String MESSAGE_TEXT_TALK_ENDED = "Разговор с волонтером завершен";
    public static final String MESSAGE_TEXT_WAS_SENT = "Ваше сообщение отправлено, волонтер скоро ответит Вам";

    // Replies stage 1. Introductory information about the shelter: where it is located, how and when it works,
    // what are the rules for admission to the territory of the shelter, the rules for staying inside and communicating with animals.
    public static final String MESSAGE_TEXT_SHELTER_INFO = "Что именно вы хотите узнать о приюте?";
    public static final String MESSAGE_TEXT_SEND_CONTACT_CHOOSE = "Выберите что хотите отправить";
    public static final String MESSAGE_TEXT_SEND_CONTACT_SUCCESS = "Данные успешно отправлены";

    // Dog shelter
    public static final String MESSAGE_DOG_SHELTER_ABOUT = "Приют для бездомных животных «HappyDog» создан Городским " +
            "благотворительным фондом «Добрые руки» в 2004 году. С момента основания приюта и до настоящего времени приют " +
            "существует на личные средства учредителей и добровольные пожертвования организаций и частных лиц. " +
            "Постоянного финансирования от бюджета приют никогда не получал. В  приюте обитают  от 500 до 600 животных. " +
            "Они содержатся в будках и в клетках-вольерах на 2 будки. Имеются небольшие помещения для кошек " +
            "и теплолюбивых собак. Для отопления установлены печи.\n" +
            "Работниками приюта налажен график выгула животных. Силами сотрудников приюта регулярно чистятся вольеры." +
            " Животных кормят 2 раза в сутки тёплой свежеприготовленной пищей. В приюте работают 8 человек: " +
            "директор приюта -  ветеринар, 5 рабочих по уходу за животными, 2 сторожа.";
    public static final String MESSAGE_DOG_SHELTER_SCHEDULE_ADDRESS = "Приют расположен по адресу Томская область, Томский район, " +
            "деревня Новомихайловка, пер.Колхозный, строение 1.\n" +
            "Время работы: Прием и выдача животных осуществляется в рабочие дни (с понедельника по пятницу) с 9:00 до 16:00." +
            "Очень желательны пожертвования от людей, которые хотят взять или сдать животное в приют. " +
            "С целью познакомиться и посмотреть животных очень просим Вас приезжать в приют до 17 часов, " +
            "поскольку животные бурно реагируют на пришедших, что вызывает недовольство людей, " +
            "проживающих в находящихся рядом с приютом частных домах.";
    public static final String MESSAGE_DOG_SHELTER_SAFETY = "Правила техники безопасности:\n" +
            "Посетители приюта обязаны всегда строго и незамедлительно следовать указаниям работников приюта.\n" +
            "Без сопровождающего работника приюта передвижение посетителей по территории приюта категорически запрещается.\n" +
            "Маршрут передвижения посетителей и посещаемые при этом животные устанавливаются работниками приюта.\n" +
            "Посетители перемещаются по указанному работником приюта маршруту организованной группой и неукоснительно соблюдают все требования работников приюта.\n" +
            "Посетители должны вести себя спокойно и тихо, поскольку животные не любят шума и суеты. Бегать и кричать категорически запрещается.\n" +
            "Если работники приюта разрешают посетителям войти в какое-то помещение, территорию или вольер, то посетители обязательно должны закрыть за собой дверь.\n" +
            "Самостоятельный выход за пределы приюта может быть осуществлен только с разрешения работников приюта.";
    public static final String MESSAGE_DOG_CAR_PASS = "Контактные данные охраны для оформления пропуска на машину";
    public static final String MESSAGE_DOG_SEND_CONTACT = "ОТПРАВКА КОНТАКТНЫХ ДАННЫХ в таблицу приюта для собак";

    // Cat shelter
    public static final String MESSAGE_CAT_SHELTER_ABOUT = "Информация о приюте для кошек";
    public static final String MESSAGE_CAT_SHELTER_SCHEDULE_ADDRESS = "Адрес и расписание работы приюта для кошек";
    public static final String MESSAGE_CAT_SHELTER_SAFETY = "Правила техники безопасности в приюте для кошек";
    public static final String MESSAGE_CAT_CAR_PASS = "Контактные данные охраны для оформления пропуска на машину";
    public static final String MESSAGE_CAT_SEND_CONTACT = "ОТПРАВКА КОНТАКТНЫХ ДАННЫХ в таблицу приюта для кошек";


    // Answers stage 2. Helping potential adopters of a dog from a shelter
    // with bureaucratic (how to draw up a contract) and everyday (how to prepare for life with a dog) questions.
    public static final String MESSAGE_TEXT_PET_INFO = "Что вас интересует?";

    // Dog shelter
    public static final String MESSAGE_DOG_RULES = "Перед посещение приюта ознакомьтесь, как правильно контактировать" +
            " с собакой по следующим пунктам:\n" +
            "1. Замедление темпа сближения.\n" +
            "2. Дайте собаке проявить инициативу.\n" +
            "3. Сигналы примирения.\n" +
            "4. Дайте себя обнюхать.\n" +
            "5. Нейтральная доброжелательность.\n" +
            "6. Нет резким движениям.\n" +
            "7. Инициатива от собаки.\n" +
            "8. То самое место.";
    public static final String MESSAGE_DOG_DOCS = "Перечень необходимых документов:\n" +
            "1) Паспорт.\n" +
            "2) Квитанция за коммунальные услуги, где отражена площадь, количество проживающих и отсутствие задолженности.\n" +
            "3) В случае съемной квартиры договор или доп.соглашение, подтверждающее возможность проживания с животными.";
    public static final String MESSAGE_DOG_TRANSPORT = "Лучше приехать за животным на своей машине или нанять такси." +
            "В общественном транспорте будет некомфортно ехать ни вам, ни собаке, " +
            "а слишком шумная обстановка может напугать животное.";
    public static final String MESSAGE_TEXT_DOG_HOUSE_CHOOSE = "О доме для какой собаки вы хотите узнать?";
    public static final String URL_DOG_HOUSE_PUPPY = "https://github.com/TamaraZolotovskaya/HappyDogBot/raw/dev/src/main/resources/static/petHomeDocs/HomeForYoungDog.pdf";
    public static final String URL_DOG_HOUSE_ADULT = "https://github.com/TamaraZolotovskaya/HappyDogBot/raw/dev/src/main/resources/static/petHomeDocs/HomeForAdultDog.pdf";
    public static final String URL_DOG_HOUSE_SICK = "https://github.com/TamaraZolotovskaya/HappyDogBot/raw/dev/src/main/resources/static/petHomeDocs/HomeForSickDog.pdf";
    public static final String URL_CAT_HOUSE_KITTY = "https://github.com/TamaraZolotovskaya/HappyDogBot/raw/dev/src/main/resources/static/petHomeDocs/HomeForYoungCat.pdf";
    public static final String URL_CAT_HOUSE_ADULT = "https://github.com/TamaraZolotovskaya/HappyDogBot/raw/dev/src/main/resources/static/petHomeDocs/HomeForAdultCat.pdf";
    public static final String URL_CAT_HOUSE_SICK = "https://github.com/TamaraZolotovskaya/HappyDogBot/raw/dev/src/main/resources/static/petHomeDocs/HomeForSickCat.pdf";
    public static final String MESSAGE_DOG_ADVICES = "Cоветы кинолога:\n" +
            "1. Поймите разницу между дрессировкой и воспитанием собаки.\n" +
            "2. Займитесь воспитание собаки с 1-го дня.\n" +
            "3. Научитесь привлекать внимание питомца и удерживать зрительный контакт.\n" +
            "4. Собака должна быть удобной! Не позволяйте делать то, что Вам не нравится с самого начала.\n" +
            "5. Научитесь переключать гнев на милость, как только собака начала слушаться и прекратила запретное действие.\n" +
            "6. Не заставляйте делать то, что собаке не нравится.\n";
    public static final String MESSAGE_DOG_CYNOLOGISTS = "Проверенные кинологи:\n" +
            "1. Федерация РФЛС Российская Федерация Любительского Собаководства\n" +
            "г. Москва,  Тел.: +7(495)482-15-10\n" +
            "2. Кинологический клуб СГОО \"КК \"Агура\" ОАНКОО/Элита\n" +
            "г. Сочи, Тел. +7(918)304-85-52";
    public static final String MESSAGE_DOG_REFUSAL = "Почему могут не дать животное в приюте:\n" +
            "1. Большое количество животных дома.\n" +
            "2. Нестабильные отношения в семье.\n" +
            "3. Наличие маленьких детей.\n" +
            "4. Съемное жилье.\n" +
            "5. Животное в подарок или для работы.\n";

    // Cat shelter
    public static final String MESSAGE_CAT_RULES = "Правила посещения приюта для кошек";
    public static final String MESSAGE_CAT_DOCS = "Необходимые документы чтобы взять кошеку";
    public static final String MESSAGE_CAT_TRANSPORT = "Рекомендации по транспортировке кошек";
    public static final String MESSAGE_CAT_HOUSE_CHOOSE = "О доме для какой кошки вы хотите узнать?";
    public static final String MESSAGE_CAT_REFUSAL = "Причины отказа";


    // Stage 3. Maintaining a pet. Replies and messages
    public static final String MESSAGE_TEXT_REPORT = "После того, как вы забрали животное из приюта, " +
            "вы обязаны в течение месяца присылать информацию о том, как оно чувствует себя на новом месте.";
    public static final String MESSAGE_TEXT_REPORT_FORM = "В ежедневный отчет входит следующая информация:\n" +
            "Необходимо отправить фото животного, а к нему, в подписи к фото, добавить следующую информацию по шаблону:\n" +
            "\n<i>Рацион: <b>ваш текст о рационе животного</b>" +
            "; Самочувствие: <b>ваш текст об общем самочувствии и привыкание к новому месту</b>" +
            "; Поведение: <b>ваш текст о повидении и об изменении поведения животного, отказ от старых привычек, приобретение новых</b></i>\n" +
            "\nОбращаем Ваше внимание на требования к отчету:\n" +
            "- необходимо написать \"Рацион:\", после него поставить пробел и написать текст, после текста рациона поставить ; и пробел\n" +
            "- необходимо написать \"Самочувствие:\", после него поставить пробел и написать текст, после текста самочувствия поставить ; и пробел\n+" +
            "- необходимо написать \"Поведение:\", после него поставить пробел и написать текст\n" +
            "- Ваш отчет не должен быть слишком коротким, но и расписывать подробности до мелочей тоже не нужно, " +
            "длина текста каждого блока должна составлять от 50 до 300 символов.";

    public static final String URL_DOG_REPORT_EXAMPLE_PHOTO = "https://raw.githubusercontent.com/TamaraZolotovskaya/HappyDogBot/dev/src/main/resources/static/Dog_report_example_photo.jpeg";

    public static final String MESSAGE_DOG_REPORT_EXAMPLE =
            "Рацион: Шарик кушает прекрасно, утром чаппи из пакетика, днем сухой корм, вечером лакомство, налитую водичку за день выпивает полностью" +
                    ";\nСамочувствие: Шарик очень активно бегает, прыгает, просит с ним поиграть, к новому месту адаптировался быстро, занет где его место" +
                    ";\nПоведение: Шарик изучил дом, знает где свое место, ночью спит там, перестал лаять на членов семьи";

    public static final String URL_CAT_REPORT_EXAMPLE_PHOTO = "https://raw.githubusercontent.com/TamaraZolotovskaya/HappyDogBot/dev/src/main/resources/static/Cat_report_example_photo.jpeg";

    public static final String MESSAGE_CAT_REPORT_EXAMPLE =
            "Рацион: <i>Мурзик кушает прекрасно, утром вискас из пакетика, днем сухой корм, вечером лакомство, налитую водичку за день выпивает полностью</i>" +
                    "; Самочувствие: <i>Мурзик очень активно бегает, прыгает, просит с ним поиграть, к новой обстановке адаптировался быстро</i>" +
                    "; Поведение: <i>Мурзик изучил дом, знает где его лежанка, ночью спит там, перестал пугаться членов семьи</i>";
    public static final String MESSAGE_TEXT_PRE_REPORT = "Загрузите фото и напишите к нему в подпись текст отчета:";

    public static final String MESSAGE_TEXT_NO_REPORT_TEXT = "Вы отправили только фото, необходимо добавить подпись к фото с текстом отчета по шаблону";

    public static final String MESSAGE_TEXT_NO_REPORT_PHOTO = "Вы отправили только текст, необходимо добавить фото с подписью - текстом отчета по шаблону";
    public static final String MESSAGE_ATTENTION_REPORT = "Не забудьте сегодня направить отчет";

    public static final String MESSAGE_TEXT_NOT_LIKE_EXAMPLE = "Текст отчета не соответствует шаблону, пожалуйста заполните текст отчета по образцу";

    public static final String MESSAGE_THANKS_FOR_REPLY = "Благодарим за отчет, волонтер проверит его и " +
            "если что-то будет не так, даст Вам обратную связь";

    // Stage 3. Responses to users on adoption decisions made
    public static final String MESSAGE_DECISION_FINISH =
            "Вы успешно прошли испытательный срок! Поздравляем с пополнением в семье!";
    public static final String MESSAGE_DECISION_EXTEND_14 =
            "Уведомляем, что по результатам Ваших отчетов продлен испытательный срок на 14 дней";
    public static final String MESSAGE_DECISION_EXTEND_30 =
            "Уведомляем, что по результатам Ваших отчетов продлен испытательный срок на 30 дней";
    public static final String MESSAGE_DECISION_REFUSE =
            "К сожалению, вынуждены сообщить, что Вам отказано в усыновлении";
}
