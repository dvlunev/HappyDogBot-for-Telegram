package com.team4.happydogbot.service;

import com.team4.happydogbot.config.BotConfig;
import com.team4.happydogbot.entity.*;
import com.team4.happydogbot.replies.Reply;
import com.team4.happydogbot.repository.AdopterCatRepository;
import com.team4.happydogbot.repository.AdopterDogRepository;
import com.team4.happydogbot.repository.ReportCatRepository;
import com.team4.happydogbot.repository.ReportDogRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.internal.verification.Times;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.team4.happydogbot.constants.BotCommands.*;
import static com.team4.happydogbot.constants.BotReplies.*;
import static com.team4.happydogbot.entity.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Test class for checking operations in the Bot service class
 *
 * @see BotConfig
 * @see AdopterDogService
 * @see AdopterDogRepository
 * @see ReportDogRepository
 * @see AdopterCatService
 * @see AdopterCatRepository
 * @see ReportCatRepository
 */

@ExtendWith(MockitoExtension.class)
public class BotTest {
    @Mock
    private BotConfig botConfig;
    @Mock
    private AdopterCatRepository adopterCatRepository;
    @Mock
    private AdopterDogRepository adopterDogRepository;
    @Mock
    private ReportDogRepository reportDogRepository;
    @Mock
    private AdopterCatService adopterCatService;
    @Mock
    private AdopterDogService adopterDogService;
    @Mock
    private ReportCatRepository reportCatRepository;
    @Spy
    @InjectMocks
    private Bot bot;

    private final AdopterDog expectedDog = new AdopterDog();
    private final AdopterCat expectedCat = new AdopterCat();

    private Reply reply = new Reply(bot);

    @Test
    @DisplayName("We check that after clicking on /start, the bot welcomes the user and offers to choose a shelter")
    public void StartTest() throws TelegramApiException {
        Update update = new Update();
        update.setMessage(new Message());
        update.getMessage().setChat(new Chat());
        update.getMessage().setText(START_CMD);
        update.getMessage().getChat().setId(111111L);
        update.getMessage().getChat().setFirstName("User");

        bot.onUpdateReceived(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot, new Times(2)).execute(argumentCaptor.capture());

        List<SendMessage> actual = argumentCaptor.getAllValues();
        assertThat(actual.get(0).getChatId()).isEqualTo(update.getMessage().getChatId().toString());
        assertThat(actual.get(0).getText()).isEqualTo("User" + MESSAGE_TEXT_GREETINGS);
        assertThat(actual.get(1).getText()).isEqualTo(MESSAGE_TEXT_CHOOSE_SHELTER);
    }


    /**
     * Method Testing <b>sendFinishListForDogVolunteer()</b> in Bot<br>
     * Mockito: when the method is called <b>AdopterDogRepository::findAll</b>,
     * returns a list containing 1 dog's adopter <b>expected</b>
     * with PROBATION status and status change date 31 days ago from the current date.<br>
     * Number of method calls <b>sendMessageWithInlineKeyboard</b> equals 1.
     */
    @Test
    @DisplayName("Checking scheduled shipments when there are dog adopters with the required status")
    public void testSendFinishListForDogVolunteer() throws Exception {
        AdopterDog expected = new AdopterDog();

        expected.setChatId(1234567890L);
        expected.setFirstName("Ivan");
        expected.setLastName("Ivanov");
        expected.setUserName("iiivanov");
        expected.setAge(33);
        expected.setAddress("МСК...");
        expected.setTelephoneNumber("7951...");
        expected.setStatusDate(LocalDate.now().minusDays(31));
        expected.setState(Status.PROBATION);


        when(adopterDogRepository.findAll()).thenReturn(List.of(expected));
        bot.sendFinishListForDogVolunteer();

        Thread.sleep(500);

        Mockito.verify(bot, Mockito.times(1))
                .sendMessageWithInlineKeyboard(0L,
                        TAKE_DECISION + expected.getUserName() + ", chatId: " + expected.getChatId(),
                        KEYBOARD_DECISION);
    }

    /**
     * Method Testing <b>sendFinishListForDogVolunteer()</b> in Bot<br>
     * Mockito: when the method is called <b>AdopterDogRepository::findAll</b>,
     * returns a list containing 1 dog's adopter <b>expected</b>
     * with REGISTRATION status and status change date 31 days ago from the current date.<br>
     * Number of method calls <b>sendMessageWithInlineKeyboard</b> equals 0.
     */
    @Test
    @DisplayName("Checking for non-scheduled shipments in the absence of dog adopters with the required status")
    public void testSendFinishListForDogVolunteerWithoutAdopter() throws Exception {
        AdopterDog expected = new AdopterDog();

        expected.setChatId(1234567890L);
        expected.setFirstName("Ivan");
        expected.setLastName("Ivanov");
        expected.setUserName("iiivanov");
        expected.setAge(33);
        expected.setAddress("МСК...");
        expected.setTelephoneNumber("7951...");
        expected.setStatusDate(LocalDate.now().minusDays(31));
        expected.setState(Status.REGISTRATION);


        when(adopterDogRepository.findAll()).thenReturn(List.of(expected));
        bot.sendFinishListForDogVolunteer();

        Thread.sleep(500);

        Mockito.verify(bot, Mockito.times(0))
                .sendMessageWithInlineKeyboard(0L,
                        TAKE_DECISION + expected.getUserName(),
                        KEYBOARD_DECISION);
    }

    /**
     * Method Testing <b>sendFinishListForCatVolunteer()</b> in Bot<br>
     * Mockito: when the method is called <b>AdopterCatRepository::findAll</b>,
     * returns a list containing 1 dog's adopter <b>expected</b>
     * with PROBATION status and status change date 31 days ago from the current date.<br>
     * Number of method calls <b>sendMessageWithInlineKeyboard</b> equals 1.
     */
    @Test
    @DisplayName("Checking planned shipments for feline adopters with the required status")
    public void testSendFinishListForCatVolunteer() throws Exception {
        AdopterCat expected = new AdopterCat();

        expected.setChatId(1234567890L);
        expected.setFirstName("Ivan");
        expected.setLastName("Ivanov");
        expected.setUserName("iiivanov");
        expected.setAge(33);
        expected.setAddress("МСК...");
        expected.setTelephoneNumber("7951...");
        expected.setStatusDate(LocalDate.now().minusDays(31));
        expected.setState(Status.PROBATION);

        when(adopterCatRepository.findAll()).thenReturn(List.of(expected));
        bot.sendFinishListForCatVolunteer();

        Thread.sleep(500);

        Mockito.verify(bot, Mockito.times(1))
                .sendMessageWithInlineKeyboard(0L,
                        TAKE_DECISION + expected.getUserName() + ", chatId: " + expected.getChatId(),
                        KEYBOARD_DECISION);
    }

    /**
     * Method Testing <b>sendFinishListForCatVolunteer()</b> in Bot<br>
     * Mockito: when the method is called <b>AdopterCatRepository::findAll</b>,
     * returns a list containing 1 dog's adopter <b>expected</b>
     * with REGISTRATION status and status change date 31 days ago from the current date.<br>
     * Number of method calls <b>sendMessageWithInlineKeyboard</b> equals 0.
     */
    @Test
    @DisplayName("Checking for non-scheduled shipments in the absence of feline adopters with the required status")
    public void testSendFinishListForCatVolunteerWithoutAdopter() throws Exception {
        AdopterCat expected = new AdopterCat();

        expected.setChatId(1234567890L);
        expected.setFirstName("Ivan");
        expected.setLastName("Ivanov");
        expected.setUserName("iiivanov");
        expected.setAge(33);
        expected.setAddress("МСК...");
        expected.setTelephoneNumber("7951...");
        expected.setStatusDate(LocalDate.now().minusDays(31));
        expected.setState(Status.REGISTRATION);

        when(adopterCatRepository.findAll()).thenReturn(List.of(expected));
        bot.sendFinishListForCatVolunteer();

        Thread.sleep(500);

        Mockito.verify(bot, Mockito.times(0))
                .sendMessageWithInlineKeyboard(0L,
                        TAKE_DECISION + expected.getUserName(),
                        KEYBOARD_DECISION);
    }

    /**
     * Method Testing <b>changeDogAdopterStatus()</b> in Bot<br>
     * Mockito: <br>
     * - when the method is called <b>AdopterDogRepository::findAll</b>;<br>
     * returns a list containing 1 dog's adopter <b>expected</b>
     * with status <b>REGISTRATION</b>;<br>
     * - when the method is called <b>AdopterDogService::get</b>,
     * returns dog adopter by id 1 <b>expected</b><br>
     * - examination <b>assertThat</b> of successfully changing the status of the adopter to <b>ADDITIONAL_PERIOD_14</b>;<br>
     * - number of method calls <b>sendMessage</b> equals 2.
     */
    @Test
    @DisplayName("Checking the status of a dog adopter")
    public void testChangeDogAdopterStatus() throws Exception {
        String messageText = "chatId: 1";
        AdopterDog expected = new AdopterDog();

        expected.setChatId(1L);
        expected.setFirstName("Ivan");
        expected.setLastName("Ivanov");
        expected.setUserName("iiivanov");
        expected.setAge(33);
        expected.setAddress("МСК...");
        expected.setTelephoneNumber("7951...");
        expected.setState(Status.REGISTRATION);

        when(adopterDogService.get(1L)).thenReturn(expected);

        bot.changeDogAdopterStatus(MESSAGE_DECISION_EXTEND_14, messageText, ADDITIONAL_PERIOD_14);

        Assertions.assertThat(expected.getState().compareTo(ADDITIONAL_PERIOD_14));
        Mockito.verify(bot, Mockito.times(2))
                .sendMessage(anyLong(), any());
    }

    /**
     * Method Testing <b>changeDogAdopterStatus()</b> in Bot<br>
     * Mockito: <br>
     * - when the method is called <b>AdopterDogRepository::findAll</b>;<br>
     * returns a list containing 1 dog's adopter <b>expected</b>
     * with status <b>REGISTRATION</b>;<br>
     * - when the method is called <b>AdopterDogService::get</b>,
     * returns dog adopter by id 1 <b>expected</b><br>
     * - examination <b>assertThat</b> of successfully changing the status of the adopter to <b>ADDITIONAL_PERIOD_14</b>;<br>
     * - number of method calls <b>sendMessage</b> equals 2.
     */
    @Test
    @DisplayName("Checking the status change of the cat's adoptive parent")
    public void testChangeCatAdopterStatus() throws Exception {
        String messageText = " chatId: 1";
        AdopterCat expected = new AdopterCat();

        expected.setChatId(1L);
        expected.setFirstName("Ivan");
        expected.setLastName("Ivanov");
        expected.setUserName("iiivanov");
        expected.setAge(33);
        expected.setAddress("МСК...");
        expected.setTelephoneNumber("7951...");
        expected.setState(Status.REGISTRATION);

        when(adopterCatService.get(1L)).thenReturn(expected);

        bot.changeCatAdopterStatus(MESSAGE_DECISION_EXTEND_14, messageText, ADDITIONAL_PERIOD_14);

        Assertions.assertThat(expected.getState().compareTo(ADDITIONAL_PERIOD_14));
        Mockito.verify(bot, Mockito.times(2))
                .sendMessage(anyLong(), any());
    }

    @Test
    @DisplayName("Verification of notification sent to volunteer and dog adopter")
    public void testSendAttentionForDogVolunteerAndAdopterDog() throws Exception {
        AdopterDog expected1 = new AdopterDog();
        AdopterDog expected2 = new AdopterDog();
        AdopterDog expected3 = new AdopterDog();

        ReportDog report1 = new ReportDog();
        ReportDog report2 = new ReportDog();
        ReportDog report3 = new ReportDog();

        expected1.setChatId(1L);
        expected1.setFirstName("Ivan");
        expected1.setLastName("Ivanov");
        expected1.setUserName("iiivanov");
        expected1.setAge(33);
        expected1.setAddress("МСК...");
        expected1.setTelephoneNumber("7951...");
        expected1.setState(PROBATION);
        expected1.setReports(List.of(report1));

        expected2.setChatId(2L);
        expected2.setFirstName("Petr");
        expected2.setLastName("Petrov");
        expected2.setUserName("pppetrov");
        expected2.setAge(33);
        expected2.setAddress("МСК...");
        expected2.setTelephoneNumber("7951...");
        expected2.setState(ADDITIONAL_PERIOD_14);
        expected1.setReports(List.of(report2));

        expected3.setChatId(3L);
        expected3.setFirstName("Denis");
        expected3.setLastName("Denisov");
        expected3.setUserName("dddenisov");
        expected3.setAge(33);
        expected3.setAddress("МСК...");
        expected3.setTelephoneNumber("7951...");
        expected3.setState(ADDITIONAL_PERIOD_30);
        expected1.setReports(List.of(report3));

        when(adopterDogRepository.findAll()).thenReturn(List.of(expected1, expected2, expected3));

        report1.setId(1L);
        report1.setAdopterDog(expected1);
        report1.setReportDate(LocalDate.now().minusDays(1));
        report1.setFileId("Test124578");
        report1.setCaption("Рацион: гуд; Самочувствие: гуд; Поведение: гуд");
        report1.setExamination(ExaminationStatus.UNCHECKED);

        report2.setId(2L);
        report2.setAdopterDog(expected2);
        report2.setReportDate(LocalDate.now().minusDays(1));
        report2.setFileId("Test986532");
        report2.setCaption("Рацион: гуд; Самочувствие: гуд; Поведение: гуд");
        report2.setExamination(ExaminationStatus.ACCEPTED);

        report3.setId(3L);
        report3.setAdopterDog(expected3);
        report3.setReportDate(LocalDate.now().minusDays(3));
        report3.setFileId("Test986532");
        report3.setCaption("Рацион: гуд; Самочувствие: гуд; Поведение: гуд");
        report3.setExamination(ExaminationStatus.ACCEPTED);


        when(reportDogRepository.findAll()).thenReturn(List.of(report1, report2, report3));

        bot.sendAttentionForDogVolunteerAndAdopterDog();

        Thread.sleep(500);

        Mockito.verify(bot, Mockito.times(1))
                .sendMessage(0L,
                        "Внимание! Необходимо проверить отчет у "
                                + expected1.getFirstName() + " " + expected1.getLastName() + " chatID: " + expected1.getChatId());

        verify(bot, Mockito.times(1))
                .sendMessage(0L, "Внимание! Усыновитель " + expected3.getFirstName()
                        + " " + expected3.getLastName() + ", username: " + expected3.getUserName()
                        + ", chatId: " + expected3.getChatId() + " уже больше 2 дней не присылает отчеты!");

        verify(bot, Mockito.times(3))
                .sendMessage(anyLong(), eq(MESSAGE_ATTENTION_REPORT));
    }

    @Test
    @DisplayName("Verification of notification sent to volunteer and dog adopter")
    public void testSendAttentionForCatVolunteerAndAdopterCat() throws Exception {
        AdopterCat expected1 = new AdopterCat();
        AdopterCat expected2 = new AdopterCat();
        AdopterCat expected3 = new AdopterCat();

        ReportCat report1 = new ReportCat();
        ReportCat report2 = new ReportCat();
        ReportCat report3 = new ReportCat();

        expected1.setChatId(1L);
        expected1.setFirstName("Ivan");
        expected1.setLastName("Ivanov");
        expected1.setUserName("iiivanov");
        expected1.setAge(33);
        expected1.setAddress("МСК...");
        expected1.setTelephoneNumber("7951...");
        expected1.setState(PROBATION);
        expected1.setReports(List.of(report1));

        expected2.setChatId(2L);
        expected2.setFirstName("Petr");
        expected2.setLastName("Petrov");
        expected2.setUserName("pppetrov");
        expected2.setAge(33);
        expected2.setAddress("МСК...");
        expected2.setTelephoneNumber("7951...");
        expected2.setState(ADDITIONAL_PERIOD_14);
        expected1.setReports(List.of(report2));

        expected3.setChatId(3L);
        expected3.setFirstName("Denis");
        expected3.setLastName("Denisov");
        expected3.setUserName("dddenisov");
        expected3.setAge(33);
        expected3.setAddress("МСК...");
        expected3.setTelephoneNumber("7951...");
        expected3.setState(ADDITIONAL_PERIOD_30);
        expected1.setReports(List.of(report3));

        when(adopterCatRepository.findAll()).thenReturn(List.of(expected1, expected2, expected3));

        report1.setId(1L);
        report1.setAdopterCat(expected1);
        report1.setReportDate(LocalDate.now().minusDays(1));
        report1.setFileId("Test124578");
        report1.setCaption("Рацион: гуд; Самочувствие: гуд; Поведение: гуд");
        report1.setExamination(ExaminationStatus.UNCHECKED);

        report2.setId(2L);
        report2.setAdopterCat(expected2);
        report2.setReportDate(LocalDate.now().minusDays(1));
        report2.setFileId("Test986532");
        report2.setCaption("Рацион: гуд; Самочувствие: гуд; Поведение: гуд");
        report2.setExamination(ExaminationStatus.ACCEPTED);

        report3.setId(2L);
        report3.setAdopterCat(expected3);
        report3.setReportDate(LocalDate.now().minusDays(3));
        report3.setFileId("Test986532");
        report3.setCaption("Рацион: гуд; Самочувствие: гуд; Поведение: гуд");
        report3.setExamination(ExaminationStatus.ACCEPTED);


        when(reportCatRepository.findAll()).thenReturn(List.of(report1, report2, report3));

        bot.sendAttentionForCatVolunteerAndAdopterCat();

        Thread.sleep(500);

        verify(bot, Mockito.times(1))
                .sendMessage(0L,
                        "Внимание! Необходимо проверить отчет у "
                                + expected1.getFirstName() + " " + expected1.getLastName() + " chatID: " + expected1.getChatId());

        verify(bot, Mockito.times(1))
                .sendMessage(0L, "Внимание! Усыновитель " + expected3.getFirstName()
                        + " " + expected3.getLastName() + ", username: " + expected3.getUserName()
                        + ", chatId: " + expected3.getChatId() + " уже больше 2 дней не присылает отчеты!");
        Mockito.verify(bot, Mockito.times(3))
                .sendMessage(anyLong(), eq(MESSAGE_ATTENTION_REPORT));
    }

    /**
     * Method Testing <b>getReport()</b> in the Bot if the picture is sent as a photo<br>
     * Mockito: when the method is called <b>adopterDogRepository::findAdopterDogByChatId</b>,
     * needed dog adopter returns <b>adopterDog</b>
     * - examination <b>assertThat</b> of successful saving of the report on all fields
     */
    @Test
    @DisplayName("Checking if the report is saved if the photo is sent as a photo")
    public void getReportDogTestAsPhoto() {
        Update update = new Update();
        update.setMessage(new Message());
        update.getMessage().setCaption("Рацион: Шарик кушает прекрасно, утром чаппи из пакетика, днем сухой корм, вечером лакомство, налитую водичку за день выпивает полностью; Самочувствие: Шарик очень активно бегает, прыгает, просит с ним поиграть, к новому месту адаптировался быстро, занет где его место; Поведение: Шарик изучил дом, знает где свое место, ночью спит там, перестал лаять на членов семьи");
        ArrayList<PhotoSize> photoSizes = new ArrayList<>();
        update.getMessage().setPhoto(photoSizes);
        PhotoSize photoSize = new PhotoSize();
        photoSizes.add(photoSize);
        photoSize.setFileId("TestFileId123");
        update.getMessage().setChat(new Chat());
        update.getMessage().getChat().setId(123L);

        AdopterDog adopterDog = new AdopterDog();
        when(adopterDogRepository.findAdopterDogByChatId(any(Long.class))).thenReturn(adopterDog);
        ReportDog expected = new ReportDog();
        expected.setFileId("TestFileId123");
        expected.setCaption("Рацион: Шарик кушает прекрасно, утром чаппи из пакетика, днем сухой корм, вечером лакомство, налитую водичку за день выпивает полностью; Самочувствие: Шарик очень активно бегает, прыгает, просит с ним поиграть, к новому месту адаптировался быстро, занет где его место; Поведение: Шарик изучил дом, знает где свое место, ночью спит там, перестал лаять на членов семьи");
        adopterDog.setReports(new ArrayList<>());
        adopterDog.getReports().add(expected);

        bot.getReport(update, true);

        ArgumentCaptor<AdopterDog> argumentCaptor = ArgumentCaptor.forClass(AdopterDog.class);
        verify(adopterDogRepository).save(argumentCaptor.capture());
        AdopterDog actualAdopter = argumentCaptor.getValue();
        ReportDog actual = actualAdopter.getReports().get(0);

        Assertions.assertThat(actual.getFileId()).isEqualTo(expected.getFileId());
        Assertions.assertThat(actual.getCaption()).isEqualTo(expected.getCaption());
        Assertions.assertThat(actual.getExamination()).isEqualTo(expected.getExamination());
        Assertions.assertThat(actual.getAdopterDog()).isEqualTo(expected.getAdopterDog());
    }

    /**
     * Method Testing <b>getReport()</b> in the Bot if the picture is sent as a photo<br>
     * Mockito: when the method is called <b>adopterDogRepository::findAdopterDogByChatId</b>,
     * needed dog adopter returns <b>adopterDog</b>
     * - examination <b>assertThat</b> of successful saving of the report on all fields
     */
    @Test
    @DisplayName("Checking if the report is saved if the photo is sent as a document")
    public void getReportCatTestAsDocument() {
        Update update = new Update();
        update.setMessage(new Message());
        update.getMessage().setCaption("Рацион: Шарик кушает прекрасно, утром чаппи из пакетика, днем сухой корм, вечером лакомство, налитую водичку за день выпивает полностью; Самочувствие: Шарик очень активно бегает, прыгает, просит с ним поиграть, к новому месту адаптировался быстро, занет где его место; Поведение: Шарик изучил дом, знает где свое место, ночью спит там, перестал лаять на членов семьи");
        Document document = new Document();
        update.getMessage().setDocument(document);
        document.setFileId("TestFileId123");
        update.getMessage().setChat(new Chat());
        update.getMessage().getChat().setId(123L);

        AdopterCat adopterCat = new AdopterCat();
        when(adopterCatRepository.findAdopterCatByChatId(any(Long.class))).thenReturn(adopterCat);
        ReportCat expected = new ReportCat();
        expected.setFileId("TestFileId123");
        expected.setCaption("Рацион: Шарик кушает прекрасно, утром чаппи из пакетика, днем сухой корм, вечером лакомство, налитую водичку за день выпивает полностью; Самочувствие: Шарик очень активно бегает, прыгает, просит с ним поиграть, к новому месту адаптировался быстро, занет где его место; Поведение: Шарик изучил дом, знает где свое место, ночью спит там, перестал лаять на членов семьи");
        adopterCat.setReports(new ArrayList<>());
        adopterCat.getReports().add(expected);

        bot.getReport(update, false);

        ArgumentCaptor<AdopterCat> argumentCaptor = ArgumentCaptor.forClass(AdopterCat.class);
        verify(adopterCatRepository).save(argumentCaptor.capture());
        AdopterCat actualAdopter = argumentCaptor.getValue();
        ReportCat actual = actualAdopter.getReports().get(0);

        Assertions.assertThat(actual.getFileId()).isEqualTo(expected.getFileId());
        Assertions.assertThat(actual.getCaption()).isEqualTo(expected.getCaption());
        Assertions.assertThat(actual.getExamination()).isEqualTo(expected.getExamination());
        Assertions.assertThat(actual.getAdopterCat()).isEqualTo(expected.getAdopterCat());
    }

    /**
     * Method Testing <b>getReport()</b> in the Bot if the caption for the photo did not pass validation<br>
     */
    @Test
    @DisplayName("Checking if the report is saved, if the caption for the photo is not validated - the report is not saved")
    public void getReportTestWithIncorrectCaption() {
        Update update = new Update();
        update.setMessage(new Message());
        update.getMessage().setCaption("Рацион: Гуд; Самочувствие: гуд; Поведение: гуд");
        update.getMessage().setChat(new Chat());
        update.getMessage().getChat().setId(123L);

        bot.getReport(update, true);
    }

    /**
     * Method Testing <b>changeUserStatusOfShelter()</b> in the Bot if the new user has selected a dog shelter<br>
     * Mockito: when the method is called <b>adopterDogRepository::findAdopterDogByChatId</b> returns <b>null</b>
     * , when the method is called <b>adopterCatRepository::findAdopterCatByChatId</b> returns <b>null</b>
     * - examination <b>assertThat</b> of successful change of status with the adopter
     */
    @Test
    @DisplayName("Checking user status change to dog shelter, new user")
    public void changeUserStatusOfShelterToDogNewUserTest() {
        Long chatId = 123L;
        boolean isDog = true;

        when(adopterDogRepository.findAdopterDogByChatId(any(Long.class))).thenReturn(null);
        when(adopterCatRepository.findAdopterCatByChatId(any(Long.class))).thenReturn(null);
        expectedDog.setChatId(chatId);
        expectedDog.setIsDog(isDog);

        bot.changeUserStatusOfShelter(chatId, isDog);

        ArgumentCaptor<AdopterDog> argumentCaptor = ArgumentCaptor.forClass(AdopterDog.class);
        verify(adopterDogRepository).save(argumentCaptor.capture());
        AdopterDog actualAdopter = argumentCaptor.getValue();

        Assertions.assertThat(actualAdopter.getChatId()).isEqualTo(expectedDog.getChatId());
        Assertions.assertThat(actualAdopter.isDog()).isEqualTo(expectedDog.isDog());
    }

    /**
     * Method Testing <b>changeUserStatusOfShelter()</b> in the Bot if the user was in a cat shelter and chose a dog shelter<br>
     * Mockito: when the method is called <b>adopterDogRepository::findAdopterDogByChatId</b> returns <b>null</b>
     * , when the method is called <b>adopterCatRepository::findAdopterCatByChatId</b> cat adopter returns <b>adopterCat</b>
     * - examination <b>assertThat</b> of successful change of status with the adopter
     */
    @Test
    @DisplayName("Checking if the user's status has changed to a dog shelter, the user has already been to a cat shelter")
    public void changeUserStatusOfShelterToDogWhenWasCatTest() {
        Long chatId = 123L;
        boolean isDog = true;

        AdopterCat adopterCat = new AdopterCat();
        adopterCat.setChatId(chatId);
        adopterCat.setIsDog(false);
        when(adopterDogRepository.findAdopterDogByChatId(any(Long.class))).thenReturn(null);
        when(adopterCatRepository.findAdopterCatByChatId(any(Long.class))).thenReturn(adopterCat);
        expectedDog.setChatId(chatId);
        expectedCat.setChatId(chatId);
        expectedDog.setIsDog(isDog);
        expectedCat.setIsDog(isDog);

        bot.changeUserStatusOfShelter(chatId, isDog);

        ArgumentCaptor<AdopterDog> argumentCaptorDog = ArgumentCaptor.forClass(AdopterDog.class);
        verify(adopterDogRepository).save(argumentCaptorDog.capture());
        AdopterDog actualAdopterDog = argumentCaptorDog.getValue();
        ArgumentCaptor<AdopterCat> argumentCaptorCat = ArgumentCaptor.forClass(AdopterCat.class);
        verify(adopterCatRepository).save(argumentCaptorCat.capture());
        AdopterCat actualAdopterCat = argumentCaptorCat.getValue();

        Assertions.assertThat(actualAdopterDog.getChatId()).isEqualTo(expectedDog.getChatId());
        Assertions.assertThat(actualAdopterDog.isDog()).isEqualTo(expectedDog.isDog());
        Assertions.assertThat(actualAdopterCat.getChatId()).isEqualTo(expectedCat.getChatId());
        Assertions.assertThat(actualAdopterCat.isDog()).isEqualTo(expectedCat.isDog());
    }

    /**
     * Method Testing <b>changeUserStatusOfShelter()</b> in Bot if a new user has chosen a cat shelter<br>
     * Mockito: when the method is called <b>adopterDogRepository::findAdopterDogByChatId</b> returns <b>null</b>
     * , when the method is called <b>adopterCatRepository::findAdopterCatByChatId</b> returns <b>null</b>
     * - examination <b>assertThat</b> of successful change of status with the adopter
     */
    @Test
    @DisplayName("Checking user status change to cat shelter, new user")
    public void changeUserStatusOfShelterToCatNewUserTest() {
        Long chatId = 123L;
        boolean isDog = false;

        when(adopterDogRepository.findAdopterDogByChatId(any(Long.class))).thenReturn(null);
        when(adopterCatRepository.findAdopterCatByChatId(any(Long.class))).thenReturn(null);
        expectedCat.setChatId(chatId);
        expectedCat.setIsDog(isDog);

        bot.changeUserStatusOfShelter(chatId, isDog);

        ArgumentCaptor<AdopterCat> argumentCaptor = ArgumentCaptor.forClass(AdopterCat.class);
        verify(adopterCatRepository).save(argumentCaptor.capture());
        AdopterCat actualAdopter = argumentCaptor.getValue();

        Assertions.assertThat(actualAdopter.getChatId()).isEqualTo(expectedCat.getChatId());
        Assertions.assertThat(actualAdopter.isDog()).isEqualTo(expectedCat.isDog());
    }

    /**
     * Method Testing <b>changeUserStatusOfShelter()</b> in the Bot if the user was in a dog shelter and chose a cat shelter<br>
     * Mockito: when the method is called <b>adopterDogRepository::findAdopterDogByChatId</b> dog adopter returns <b>adopterDog</b>
     * , when the method is called <b>adopterCatRepository::findAdopterCatByChatId</b> returns <b>null</b>
     * - examination <b>assertThat</b> of successful change of status with the adopter
     */
    @Test
    @DisplayName("Checking for a user status change to a cat shelter, the user has already been to a dog shelter")
    public void changeUserStatusOfShelterToCatWhenWasDogTest() {
        Long chatId = 123L;
        boolean isDog = false;

        AdopterDog adopterDog = new AdopterDog();
        adopterDog.setChatId(chatId);
        adopterDog.setIsDog(true);
        when(adopterDogRepository.findAdopterDogByChatId(any(Long.class))).thenReturn(adopterDog);
        when(adopterCatRepository.findAdopterCatByChatId(any(Long.class))).thenReturn(null);
        expectedDog.setChatId(chatId);
        expectedCat.setChatId(chatId);
        expectedDog.setIsDog(isDog);
        expectedCat.setIsDog(isDog);

        bot.changeUserStatusOfShelter(chatId, isDog);

        ArgumentCaptor<AdopterDog> argumentCaptorDog = ArgumentCaptor.forClass(AdopterDog.class);
        verify(adopterDogRepository).save(argumentCaptorDog.capture());
        AdopterDog actualAdopterDog = argumentCaptorDog.getValue();
        ArgumentCaptor<AdopterCat> argumentCaptorCat = ArgumentCaptor.forClass(AdopterCat.class);
        verify(adopterCatRepository).save(argumentCaptorCat.capture());
        AdopterCat actualAdopterCat = argumentCaptorCat.getValue();

        Assertions.assertThat(actualAdopterDog.getChatId()).isEqualTo(expectedDog.getChatId());
        Assertions.assertThat(actualAdopterDog.isDog()).isEqualTo(expectedDog.isDog());
        Assertions.assertThat(actualAdopterCat.getChatId()).isEqualTo(expectedCat.getChatId());
        Assertions.assertThat(actualAdopterCat.isDog()).isEqualTo(expectedCat.isDog());
    }

    /**
     * Method Testing <b>forwardMessageToVolunteer()</b> in Bot<br>
     * - examination <b>assertThat</b> successful redirect message to user
     */
    @Test
    @DisplayName("Checking the method of forwarding a message to a volunteer from a user")
    public void forwardMessageToVolunteerTest() throws TelegramApiException {
        Long chatId = 123L;
        int messageId = 456;

        bot.forwardMessageToVolunteer(chatId, messageId);

        ArgumentCaptor<ForwardMessage> argumentCaptor = ArgumentCaptor.forClass(ForwardMessage.class);
        verify(bot).execute(argumentCaptor.capture());
        ForwardMessage actual = argumentCaptor.getValue();
        assertThat(actual.getFromChatId()).isEqualTo(chatId.toString());
        assertThat(actual.getMessageId()).isEqualTo(messageId);
    }

    /**
     * Method Testing <b>talkWithVolunteerOrNoSuchCommand()</b> in Bot<br>
     * - examination <b>assertThat</b> successfully sending a message to the user that there is no such command
     */
    @Test
    @DisplayName("Checking the method of speaking with a volunteer, there is no such command")
    public void talkWithVolunteerOrNoSuchCommandTest() throws TelegramApiException {
        Update update = new Update();
        update.setMessage(new Message());
        update.getMessage().setChat(new Chat());
        update.getMessage().getChat().setId(123L);
        update.getMessage().setText("Test message");

        bot.talkWithVolunteerOrNoSuchCommand(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();
        assertThat(actual.getChatId()).isEqualTo("123");
        assertThat(actual.getText()).isEqualTo("Нет такой команды");
    }
}
