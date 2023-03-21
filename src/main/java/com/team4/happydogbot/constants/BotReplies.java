package com.team4.happydogbot.constants;


/**
 * Класс содержит константы с ответами на команды
 */
public class BotReplies {

    // Ответы этап 0. Входная точка общения бота с пользователем
    public static final String MESSAGE_TEXT_GREETINGS = ", приветствую! Чтобы найти то, что вам нужно - нажмите на кнопку";
    public static final String MESSAGE_TEXT_CHOOSE_SHELTER = "Выберите приют";
    public static final String MESSAGE_TEXT_CHOOSE_ACTION = "Выберите действие";
    public static final String MESSAGE_TEXT_NO_COMMAND = "Нет такой команды";
    public static final String MESSAGE_TEXT_WRITE_VOLUNTEER = "Напишите волонтеру:";
    public static final String MESSAGE_TEXT_TALK_ENDED = "Разговор с волонтером завершен";
    public static final String MESSAGE_TEXT_WAS_SENT = "Ваше сообщение отправлено, волонтер скоро ответит Вам";

    // Ответы этап 1. Вводная информация о приюте: где он находится, как и когда работает,
    // какие правила пропуска на территорию приюта, правила нахождения внутри и общения с животными.
    public static final String MESSAGE_TEXT_SHELTER_INFO = "Что именно вы хотите узнать о приюте?";

    //Приют для собак
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
            "деревня Новомихайловка, пер.Колхозный, строение 1.\n"+
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

    //Приют для кошек
    public static final String MESSAGE_CAT_SHELTER_ABOUT = "Информация о приюте для кошек";
    public static final String MESSAGE_CAT_SHELTER_SCHEDULE_ADDRESS = "Адрес и расписание работы приюта для кошек";
    public static final String MESSAGE_CAT_SHELTER_SAFETY = "Правила техники безопасности в приюте для кошек";
    public static final String MESSAGE_CAT_CAR_PASS = "Контактные данные охраны для оформления пропуска на машину";
    public static final String MESSAGE_CAT_SEND_CONTACT = "ОТПРАВКА КОНТАКТНЫХ ДАННЫХ в таблицу приюта для кошек";



    // Ответы этап 2. Помощь потенциальным «усыновителям» собаки из приюта
    // с бюрократическими (как оформить договор) и бытовыми (как подготовиться к жизни с собакой) вопросами.
    public static final String MESSAGE_TEXT_PET_INFO = "Что вас интересует?";

    //Приют для собак
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

    /*public static final String MESSAGE_TEXT_PET_HOUSE_PUPPY = "Если вы планируете заводить щенка, необходимо:\n" +
            "1. Позаботьтесь о безопасности в доме. Собаки могут грызть провода и играть с ними, " +
            "поэтому все электрокабели необходимо провести по верху или спрятать в специальные короба под плинтус. " +
            "Уберите потенциально опасные предметы со столиков и нижних полок: стеклянные и фарфоровые вазы, " +
            "мелкие безделушки, бытовую химию.\n" +
            "2. Подумайте о том, где будет располагаться собачье место, поставьте туда мягкую лежанку или клетку. " +
            "Когда питомец появится в доме, проследите, где он чаще всего отдыхает, и перенесите лежанку туда.\n" +
            "3. Купите для щенка все необходимое. Для прогулок вам потребуется поводок, а лучше два: длинный и короткий. " +
            "При выборе ошейника позаботьтесь об \"адреснике\" с контактами хозяина, без него не стоит " +
            "выходить на прогулку с животным. " +
            "Для тех щенков, что не приучены еще к улице заранее купите пеленки и чистящие средства на основе энзимов: " +
            "в доме должно быть место, где питомец может сходить в туалет в случае необходимости. \n" +
            "4. Подберите корм, миски купите минимум две — одну для воды, другую для еды. " +
            "Лучше всего подойдут керамические или металлические, тогда собака не сможет их сгрызть. \n" +
            "5. Вам потребуются средства для ухода за питомцем: щетка для расчесывания шерсти, шампунь для мытья, " +
            "зубная паста и щетка, лосьоны для чистки глаз и ушей.\n";
    public static final String MESSAGE_DOG_HOUSE_ADULT = "Если вы планируете заводить взрослую собаку, необходимо:\n" +
            "1. Позаботьтесь о безопасности в доме. Собаки могут грызть провода и играть с ними, " +
            "поэтому все электрокабели необходимо провести по верху или спрятать в специальные короба под плинтус. " +
            "Уберите потенциально опасные предметы со столиков и нижних полок: стеклянные и фарфоровые вазы, " +
            "мелкие безделушки, бытовую химию.\n" +
            "2. Подумайте о том, где будет располагаться собачье место, поставьте туда мягкую лежанку или клетку. " +
            "Когда питомец появится в доме, проследите, где он чаще всего отдыхает, и перенесите лежанку туда.\n" +
            "3. Купите для собаки все необходимое. Для прогулок вам потребуется поводок, а лучше два: длинный и короткий. " +
            "При выборе ошейника позаботьтесь об \"адреснике\" с контактами хозяина, без него не стоит " +
            "выходить на прогулку с животным. " +
            "4. Подберите корм, миски купите минимум две — одну для воды, другую для еды. " +
            "Лучше всего подойдут керамические или металлические, тогда собака не сможет их сгрызть. \n" +
            "5. Вам потребуются средства для ухода за питомцем: щетка для расчесывания шерсти, шампунь для мытья, " +
            "зубная паста и щетка, лосьоны для чистки глаз и ушей.\n";
    public static final String MESSAGE_DOG_HOUSE_SICK = "Если вы планируете заводить собаку c ограниченными возможностями, необходимо:\n" +
            "1. Позаботьтесь о безопасности в доме. Собаки могут грызть провода и играть с ними, " +
            "поэтому все электрокабели необходимо провести по верху или спрятать в специальные короба под плинтус. " +
            "Уберите все торчащие предметы, которые могут поранить собаку, огородите лестницы, закройте люки," +
            "уберите потенциально опасные предметы со столиков и нижних полок: стеклянные и фарфоровые вазы, " +
            "мелкие безделушки, бытовую химию.\n" +
            "2. Подумайте о том, где будет располагаться собачье место, поставьте туда мягкую лежанку или клетку. " +
            "Когда питомец появится в доме, проследите, где он чаще всего отдыхает, и перенесите лежанку туда.\n" +
            "3. Купите для собаки все необходимое. Для прогулок вам потребуется поводок, а лучше два: длинный и короткий. " +
            "При выборе ошейника позаботьтесь об \"адреснике\" с контактами хозяина, без него не стоит " +
            "выходить на прогулку с животным. " +
            "4. Подберите корм, миски купите минимум две — одну для воды, другую для еды. " +
            "Лучше всего подойдут керамические или металлические, тогда собака не сможет их сгрызть. \n" +
            "5. Вам потребуются средства для ухода за питомцем: щетка для расчесывания шерсти, шампунь для мытья, " +
            "зубная паста и щетка, лосьоны для чистки глаз и ушей.\n" +
            "6. Уточните у волонтера какие именно ограничения у выбранного питомца, чтобы лучше подготовиться";*/
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

    //Приют для кошек
    public static final String MESSAGE_CAT_RULES = "Правила посещения приюта для кошек";
    public static final String MESSAGE_CAT_DOCS = "Необходимые документы чтобы взять кошеку";
    public static final String MESSAGE_CAT_TRANSPORT = "Рекомендации по транспортировке кошек";
    public static final String MESSAGE_CAT_HOUSE_CHOOSE = "О доме для какой кошки вы хотите узнать?";
    public static final String MESSAGE_CAT_REFUSAL = "Причины отказа";


    //Этап 3. Ведение питомца. Ответы и сообщения
    public static final String MESSAGE_TEXT_REPORT = "После того как вы забрали животное из приюта, " +
            "вы обязаны в течение месяца присылать информацию о том, как оно чувствует себя на новом месте.";
    public static final String MESSAGE_TEXT_REPORT_FORM = "В ежедневный отчет входит следующая информация: \n" +
            "- Фото животного.\n" +
            "- Рацион животного.\n" +
            "- Общее самочувствие и привыкание к новому месту.\n" +
            "- Изменение в поведении: отказ от старых привычек, приобретение новых.";




}
