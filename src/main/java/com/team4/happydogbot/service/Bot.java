package com.team4.happydogbot.service;

import com.team4.happydogbot.config.BotConfig;
import com.team4.happydogbot.constants.BotCommands;
import com.team4.happydogbot.entity.*;
import com.team4.happydogbot.replies.Reply;
import com.team4.happydogbot.repository.AdopterCatRepository;
import com.team4.happydogbot.repository.AdopterDogRepository;
import com.team4.happydogbot.repository.ReportCatRepository;
import com.team4.happydogbot.repository.ReportDogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.*;

import static com.team4.happydogbot.constants.BotCommands.*;
import static com.team4.happydogbot.constants.BotReplies.*;
import static com.team4.happydogbot.constants.PatternValidation.validationPatternReport;
import static com.team4.happydogbot.entity.Status.*;

@Slf4j
@Service
public class Bot extends TelegramLongPollingBot {

    private final BotConfig config;

    private final AdopterDogRepository adopterDogRepository;

    private final AdopterCatRepository adopterCatRepository;

    private final ReportDogRepository reportDogRepository;
    private final ReportCatRepository reportCatRepository;
    private final AdopterDogService adopterDogService;
    private final AdopterCatService adopterCatService;

    public Bot(BotConfig config, AdopterDogRepository adopterDogRepository, AdopterCatRepository adopterCatRepository,
               ReportDogRepository reportDogRepository, ReportCatRepository reportCatRepository,
               AdopterDogService adopterDogService, AdopterCatService adopterCatService) {
        this.config = config;
        this.adopterDogRepository = adopterDogRepository;
        this.adopterCatRepository = adopterCatRepository;
        this.reportDogRepository = reportDogRepository;
        this.reportCatRepository = reportCatRepository;
        this.adopterDogService = adopterDogService;
        this.adopterCatService = adopterCatService;
    }

    public static final Map<String, Long> REQUEST_FROM_USER = new HashMap<>();

    public static final Set<Long> REQUEST_GET_REPLY_FROM_USER = new HashSet<>();

    private final Reply reply = new Reply(this);

    private static final ResourceBundle resource = ResourceBundle.getBundle("application");

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (START_CMD.equals(messageText)) {
                sendMessage(chatId, update.getMessage().getChat().getFirstName() + MESSAGE_TEXT_GREETINGS);
                sendMessage(chatId, MESSAGE_TEXT_CHOOSE_SHELTER, replyKeyboardShelter());
            } else if ((adopterCatRepository.findAdopterCatByChatId(chatId) != null &&
                    !adopterCatRepository.findAdopterCatByChatId(chatId).isDog()) &&
                    reply.catReplies.containsKey(messageText)) {
                reply.catReplies.get(messageText).accept(chatId);
            } else if (reply.dogReplies.containsKey(messageText)) {
                reply.dogReplies.get(messageText).accept(chatId);
            } else if (CALL_VOLUNTEER_CMD.equals(messageText)) {
                REQUEST_FROM_USER.put(messageText, chatId);
                sendMessageWithInlineKeyboard(chatId, MESSAGE_TEXT_WRITE_VOLUNTEER, FINISH_VOLUNTEER_CMD);
                // Кладем в HashMap сообщение в кач-ве ключа и chatId в кач-ве значения того, кто позвал волонтера,
                // то есть пока в HashMap лежит текст и chatId - это значит что юзер находится в состоянии разговора с волонтером,
                // отправляем сообщение пользователю
            } else if (REQUEST_GET_REPLY_FROM_USER.contains(chatId)) {
                sendMessageWithInlineKeyboard(update.getMessage().getChatId(), MESSAGE_TEXT_NO_REPORT_PHOTO, REPORT_EXAMPLE, SEND_REPORT);
            } else talkWithVolunteerOrNoSuchCommand(update);

        } else if (update.hasCallbackQuery()) {
            String messageData = update.getCallbackQuery().getData();
            String messageText = update.getCallbackQuery().getMessage().getText();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            if (adopterCatRepository.findAdopterCatByChatId(chatId) != null &&
                    !adopterCatRepository.findAdopterCatByChatId(chatId).isDog() &&
                    reply.catReplies.containsKey(messageData)) {
                reply.catReplies.get(messageData).accept(chatId);
            } else if (reply.dogReplies.containsKey(messageData)) {
                reply.dogReplies.get(messageData).accept(chatId);
            } else if (CALL_VOLUNTEER_CMD.equals(messageData)) {
                REQUEST_FROM_USER.put(messageData, chatId);
                sendMessageWithInlineKeyboard(chatId, MESSAGE_TEXT_WRITE_VOLUNTEER, FINISH_VOLUNTEER_CMD);
            } else if (SEND_REPORT.equals(messageData)) {
                REQUEST_GET_REPLY_FROM_USER.add(chatId);
                sendMessage(chatId, MESSAGE_TEXT_PRE_REPORT);
                // Кладем в HashSet chatId пользователя, который нажал кнопку "Отправить отчет", то есть пока в HashMap
                // лежит chatId - это значит что юзер находится в состоянии отправки отчета,
                // отправляем сообщение пользователю
            } else if (FINISH_PROBATION.equals(messageData)
                    && adopterDogRepository.findAdopterDogByChatId(chatId).isDog()) {
                //метод изменения статуса на Finished и информирования пользователя для собак
                changeDogAdopterStatus(MESSAGE_DECISION_FINISH, messageText, FINISHED_PROBATION_PERIOD);
            } else if (FINISH_PROBATION.equals(messageData)) {
                //метод изменения статуса на Finished и информирования пользователя для кошек
                changeCatAdopterStatus(MESSAGE_DECISION_FINISH, messageText, FINISHED_PROBATION_PERIOD);
            } else if (EXTEND_PROBATION_14.equals(messageData)
                    && adopterDogRepository.findAdopterDogByChatId(chatId).isDog()) {
                //метод изменения статуса на Additional_14 и информирования пользователя для собак
                changeDogAdopterStatus(MESSAGE_DECISION_EXTEND_14, messageText, ADDITIONAL_PERIOD_14);
            } else if (EXTEND_PROBATION_14.equals(messageData)) {
                //метод изменения статуса на Additional_14 и информирования пользователя для кошек
                changeCatAdopterStatus(MESSAGE_DECISION_EXTEND_14, messageText, ADDITIONAL_PERIOD_14);
            } else if (EXTEND_PROBATION_30.equals(messageData)
                    && adopterDogRepository.findAdopterDogByChatId(chatId).isDog()) {
                //метод изменения статуса на Additional_30 и информирования пользователя для собак
                changeDogAdopterStatus(MESSAGE_DECISION_EXTEND_30, messageText, ADDITIONAL_PERIOD_30);
            } else if (EXTEND_PROBATION_30.equals(messageData)) {
                //метод изменения статуса на Additional_30 и информирования пользователя для кошек
                changeCatAdopterStatus(MESSAGE_DECISION_EXTEND_30, messageText, ADDITIONAL_PERIOD_30);
            } else if (REFUSE.equals(messageData)
                    && adopterDogRepository.findAdopterDogByChatId(chatId).isDog()) {
                //метод изменения статуса на Refuse и информирования пользователя для собак
                changeDogAdopterStatus(MESSAGE_DECISION_REFUSE, messageText, ADOPTION_DENIED);
            } else if (REFUSE.equals(messageData)) {
                //метод изменения статуса на Refuse и информирования пользователя для кошек
                changeCatAdopterStatus(MESSAGE_DECISION_REFUSE, messageText, ADOPTION_DENIED);
            } else sendMessage(chatId, MESSAGE_TEXT_NO_COMMAND);

        } else if (update.hasMessage() && (update.getMessage().hasPhoto() || update.getMessage().hasDocument())) {
            long chatId = update.getMessage().getChatId();
            if (REQUEST_GET_REPLY_FROM_USER.contains(chatId) &&
                    adopterDogRepository.findAdopterDogByChatId(chatId) != null &&
                    adopterDogRepository.findAdopterDogByChatId(chatId).isDog()) {
                if (update.getMessage().getCaption() == null) {
                    sendMessageWithInlineKeyboard(update.getMessage().getChatId(), MESSAGE_TEXT_NO_REPORT_TEXT, REPORT_EXAMPLE, SEND_REPORT);
                } else {
                    getReport(update, true);
                }
            } else if (REQUEST_GET_REPLY_FROM_USER.contains(chatId)) {
                if (update.getMessage().getCaption() == null) {
                    sendMessageWithInlineKeyboard(update.getMessage().getChatId(), MESSAGE_TEXT_NO_REPORT_TEXT, REPORT_EXAMPLE, SEND_REPORT);
                } else {
                    getReport(update, false);
                }

            } else sendMessage(chatId, MESSAGE_TEXT_NO_COMMAND);

        } else if (update.hasMessage() && update.getMessage().hasContact()) {
            long chatId = update.getMessage().getChatId();
            processContact(update);
            sendMessage(chatId, MESSAGE_TEXT_SEND_CONTACT_SUCCESS);
        }
    }

    /**
     * The method checks the text part of the report for compliance with the template, if it is a photo, selects the maximum size,
     * if it's a file, get the fileId and write the report data to the cat or dog database, remove the send state
     * report - removes the chatId from the HashSet where the users who clicked the "Send report" button are stored
     * Methods used:<br>
     * {@link #sendMessageWithInlineKeyboard(long chatId, String textToSend, String... buttons)}<br>
     * {@link #sendMessage(long chatId, String textToSend)}
     *
     * @param update incoming update of the bot with a photo or a file (a file in case the user sends an uncompressed photo)
     * @param isDog  the status of the selected shelter at Adopter
     */
    public void getReport(Update update, boolean isDog) {
        if (validationPatternReport(update.getMessage().getCaption())) {
            String reportText = update.getMessage().getCaption();
            String fileId;
            if (update.getMessage().hasPhoto()) {
                List<PhotoSize> photoSizes = update.getMessage().getPhoto();
                PhotoSize photoSize = photoSizes.stream()
                        .max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);
                fileId = photoSize.getFileId();
            } else {
                Document document = update.getMessage().getDocument();
                fileId = document.getFileId();
            }
            if (isDog) {
                AdopterDog adopterDog = adopterDogRepository.findAdopterDogByChatId(update.getMessage().getChatId());
                ReportDog reportDog = new ReportDog();
                reportDog.setReportDate(LocalDate.now());
                reportDog.setFileId(fileId);
                reportDog.setCaption(reportText);
                reportDog.setAdopterDog(adopterDog);
                adopterDog.getReports().add(reportDog);
                adopterDogRepository.save(adopterDog);
            } else {
                AdopterCat adopterCat = adopterCatRepository.findAdopterCatByChatId(update.getMessage().getChatId());
                ReportCat reportCat = new ReportCat();
                reportCat.setReportDate(LocalDate.now());
                reportCat.setFileId(fileId);
                reportCat.setCaption(reportText);
                reportCat.setAdopterCat(adopterCat);
                adopterCat.getReports().add(reportCat);
                adopterCatRepository.save(adopterCat);
            }
            REQUEST_GET_REPLY_FROM_USER.remove(update.getMessage().getChatId());
            sendMessage(update.getMessage().getChatId(), MESSAGE_THANKS_FOR_REPLY);
        } else {
            REQUEST_GET_REPLY_FROM_USER.remove(update.getMessage().getChatId());
            sendMessageWithInlineKeyboard(update.getMessage().getChatId(), MESSAGE_TEXT_NOT_LIKE_EXAMPLE, REPORT_EXAMPLE, SEND_REPORT);
        }
    }

    /**
     * The method sends the user a photo with a caption and a keyboard
     *
     * @param chatId   user ID
     * @param fileUrl  URL of the photo, the photo must be stored on the server
     * @param keyboard keyboard
     */
    public void sendPhotoWithCaption(long chatId, String caption, String fileUrl, ReplyKeyboard keyboard) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(chatId));
        sendPhoto.setCaption(caption);
        sendPhoto.setPhoto(new InputFile(fileUrl));
        sendPhoto.setReplyMarkup(keyboard);
        sendPhoto.setParseMode(ParseMode.HTML);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    /**
     * The method sends the user a photo with a caption with InlineKeyboard<br>
     * Used methods
     * {@link #sendPhotoWithCaption(long, String, String, ReplyKeyboard)}
     * {@link #InlineKeyboardMaker(String...)}
     *
     * @param chatId     user ID
     * @param textToSend Message text
     * @param fileUrl    URL of the photo, the photo must be stored on the server
     * @param buttons    set (array or varargs) of keyboard buttons
     */
    public void sendPhotoWithCaptionWithInlineKeyboard(long chatId, String textToSend, String fileUrl, String... buttons) {
        InlineKeyboardMarkup inlineKeyboard = InlineKeyboardMaker(buttons);
        sendPhotoWithCaption(chatId, textToSend, fileUrl, inlineKeyboard);
    }

    /**
     * The method sends a document to the user
     *
     * @param chatId  user ID
     * @param fileUrl Document URL, the document must be stored on the server
     */
    public void sendDocument(long chatId, String fileUrl) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(String.valueOf(chatId));
        sendDocument.setCaption("Информация по вашему вопросу содержится в файле");
        sendDocument.setDocument(new InputFile(fileUrl));
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    /**
     * The method sends a message to the user
     *
     * @param chatId     user ID
     * @param textToSend message text
     * @throws TelegramApiException
     */
    public void sendMessage(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        sendMessage.setParseMode(ParseMode.HTML);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    /**
     * The method sends a message with the keyboard
     *
     * @param chatId     user ID
     * @param textToSend message text
     * @param keyboard   keyboard
     * @throws TelegramApiException
     */
    public void sendMessage(long chatId, String textToSend, ReplyKeyboard keyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        sendMessage.setReplyMarkup(keyboard);
        sendMessage.setParseMode(ParseMode.HTML);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    /**
     * The method sends a message with InlineKeyboard<br>
     * Used methods
     * {@link #sendMessage(long, String, ReplyKeyboard)}
     * {@link #InlineKeyboardMaker(String...)}
     *
     * @param chatId     user ID
     * @param textToSend message text
     * @param buttons    set (array or varargs) of keyboard buttons
     */
    public void sendMessageWithInlineKeyboard(long chatId, String textToSend, String... buttons) {
        InlineKeyboardMarkup inlineKeyboard = InlineKeyboardMaker(buttons);
        sendMessage(chatId, textToSend, inlineKeyboard);
    }

    /**
     * The method creates an InlineKeyboard
     *
     * @param buttons set (array or varargs) of keyboard buttons
     * @return message keyboard
     */
    public InlineKeyboardMarkup InlineKeyboardMaker(String... buttons) {
        InlineKeyboardMarkup inlineKeyboardAbout = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        //создаем кнопки
        for (String buttonText :
                buttons) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(buttonText);
            button.setCallbackData(buttonText);
            List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
            rowInLine1.add(button);
            rowsInLine.add(rowInLine1);
        }
        inlineKeyboardAbout.setKeyboard(rowsInLine);

        return inlineKeyboardAbout;
    }

    /**
     * The method creates a keyboard at the bottom of the screen
     * This keyboard is always available to the user
     *
     * @return keyboard with command options
     */
    public ReplyKeyboardMarkup replyKeyboardBottom() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardRow1 = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardRow1.add(SHELTER_INFO_CMD);
        keyboardRow1.add(PET_INFO_CMD);

        // Вторая строчка клавиатуры
        KeyboardRow keyboardRow2 = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardRow2.add(SEND_REPORT_CMD);
        keyboardRow2.add(CALL_VOLUNTEER_CMD);

        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRow3.add(SHELTER_CHOOSE);

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardRow1);
        keyboard.add(keyboardRow2);
        keyboard.add(keyboardRow3);

        // и устанавливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    /**
     * The method creates a keyboard for choosing a shelter at the bottom of the screen
     *
     * @return keyboard with command options
     */
    public ReplyKeyboardMarkup replyKeyboardShelter() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(SHELTER_CAT);
        keyboardRow1.add(SHELTER_DOG);
        keyboard.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    /**
     * The method finds and deletes the last volunteer request from the user by the user's chatId
     *
     * @param chatId chat ID of the user who called the volunteer and wrote a message to the volunteer
     */
    public void findAndRemoveRequestFromUser(long chatId) {
        for (Map.Entry<String, Long> stringLongEntry : REQUEST_FROM_USER.entrySet()) {
            if (stringLongEntry.getValue() == chatId) {
                REQUEST_FROM_USER.remove(stringLongEntry.getKey());
                break;
            }
        }
    }

    /**
     * The method sends a message to the volunteer with the messageId from the user with the chatId of the user who called the volunteer
     *
     * @param chatId    chat ID of the user who called the volunteer and wrote a message to the volunteer
     * @param messageId identifier of the message sent to the volunteer
     */
    public void forwardMessageToVolunteer(long chatId, int messageId) {
        ForwardMessage forwardMessage = new ForwardMessage(String.valueOf(config.getVolunteerChatId()), String.valueOf(chatId), messageId);
        try {
            execute(forwardMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    /**
     * The method describes the state of a conversation with a volunteer or sending a default command, namely:<br>
     * - either forwards the volunteer's message from the user,<br>
     * - either sends a message to the user from a volunteer,<br>
     * - or if the user is not in a conversation with a volunteer, reports that there is no such command<br>
     * Methods used:<br>
     * {@link #findAndRemoveRequestFromUser(long chatId)}<br>
     * {@link #forwardMessageToVolunteer(long chatId, int messageId)}<br>
     * {@link #sendMessage(long chatId, String textToSend)}
     *
     * @param update received user text message<br>
     */
    public void talkWithVolunteerOrNoSuchCommand(Update update) {
        long chatId = update.getMessage().getChatId();
        if (REQUEST_FROM_USER.containsValue(chatId)) {
            // Если в мапе уже есть chatId того кто написал боту, то есть продолжается общение с волонтером,
            // то удаляем предыдущее сообщение и записываем новое сообщение, отправляем сообщение волонтеру
            findAndRemoveRequestFromUser(chatId);
            REQUEST_FROM_USER.put(update.getMessage().getText(), chatId);
            forwardMessageToVolunteer(chatId, update.getMessage().getMessageId());
            sendMessage(chatId, MESSAGE_TEXT_WAS_SENT);
        } else if (config.getVolunteerChatId() == chatId
                // Если сообщение поступило от волонтера и содержит Reply на другое сообщение и текст в
                // Reply совпадает с тем что в мапе, то это сообщение отправляем юзеру
                && update.getMessage().getReplyToMessage() != null
                && REQUEST_FROM_USER.containsKey(update.getMessage().getReplyToMessage().getText())) {
            String s = update.getMessage().getReplyToMessage().getText();
            sendMessageWithInlineKeyboard(
                    REQUEST_FROM_USER.get(s), // получаем chatId по сообщению на которое отвечаем
                    "Сообщение от волонтера " + update.getMessage().getChat().getFirstName() + ":\n<i>" +
                            update.getMessage().getText() + "</i>\n" + "\n" + MESSAGE_TEXT_WRITE_VOLUNTEER,
                    FINISH_VOLUNTEER_CMD);
        } else {
            // Если сообщение не подходит не под одну команду и волонтер и юзер не находятся в состоянии
            // разговора, то выводим сообщение нет такой команды
            sendMessage(chatId, MESSAGE_TEXT_NO_COMMAND);
        }
    }

    /**
     * The method changes the state of the isDog field - the state of the selected shelter in Adopter
     * Methods used:<br>
     *
     * @param chatId chat ID of the user who chose/changed shelter
     * @param isDog  the status of the selected shelter at Adopter
     */
    public void changeUserStatusOfShelter(Long chatId, boolean isDog) {
        AdopterDog adopterDog = adopterDogRepository.findAdopterDogByChatId(chatId);
        AdopterCat adopterCat = adopterCatRepository.findAdopterCatByChatId(chatId);
        if (isDog) {
            if (adopterDog == null) {
                adopterDog = new AdopterDog();
                adopterDog.setChatId(chatId);
            }
            adopterDog.setIsDog(true);
            adopterDogRepository.save(adopterDog);
            if (adopterCat != null) {
                adopterCat.setIsDog(true);
                adopterCatRepository.save(adopterCat);
            }
        } else {
            if (adopterCat == null) {
                adopterCat = new AdopterCat();
                adopterCat.setChatId(chatId);
            }
            adopterCat.setIsDog(false);
            adopterCatRepository.save(adopterCat);
            if (adopterDog != null) {
                adopterDog.setIsDog(false);
                adopterDogRepository.save(adopterDog);
            }
        }
    }

    /**
     * Creates a keyboard and sends a message with it to get the user's contact information
     *
     * @param chatId user chat id
     */
    public void sendMessageWithContactKeyboard(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardButton contact = new KeyboardButton(SEND_CONTACT_CMD);
        contact.setRequestContact(true);
        keyboardRow1.add(contact);

        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow2.add(BACK_CMD);

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(keyboardRow1);
        keyboard.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage(chatId, MESSAGE_TEXT_SEND_CONTACT_CHOOSE, replyKeyboardMarkup);
    }

    /**
     * Processes the contact data sent by the user and writes their database
     *
     * @param update accepted user contact
     */
    private void processContact(Update update) {
        User user = update.getMessage().getFrom();
        long chatId = update.getMessage().getChatId();
        if (adopterCatRepository.findAdopterCatByChatId(chatId) != null
                && !adopterCatRepository.findAdopterCatByChatId(chatId).isDog()) {
            AdopterCat adopterCat = adopterCatRepository.findAdopterCatByChatId(chatId);
            adopterCat.setChatId(user.getId());
            adopterCat.setFirstName(user.getFirstName());
            adopterCat.setLastName(user.getLastName());
            adopterCat.setUserName(user.getUserName());
            adopterCat.setTelephoneNumber(update.getMessage().getContact().getPhoneNumber());
            adopterCat.setState(REGISTRATION);
            adopterCatRepository.save(adopterCat);
        } else if (adopterDogRepository.findAdopterDogByChatId(chatId) != null
                && adopterDogRepository.findAdopterDogByChatId(chatId).isDog()) {
            AdopterDog adopterDog = adopterDogRepository.findAdopterDogByChatId(chatId);
            adopterDog.setChatId(user.getId());
            adopterDog.setFirstName(user.getFirstName());
            adopterDog.setLastName(user.getLastName());
            adopterDog.setUserName(user.getUserName());
            adopterDog.setTelephoneNumber(update.getMessage().getContact().getPhoneNumber());
            adopterDog.setState(REGISTRATION);
            adopterDogRepository.save(adopterDog);
        }
    }

    /**
     * The method organizes according to the schedule an automatic check for the availability of reports by the registration period
     * according to the following algorithm:<br>
     * - getting Adopter list with status PROBATION, ADDITIONAL_PERIOD_14, ADDITIONAL_PERIOD_30;<br>
     * - getting for each Adopter from the list of its reports the last report with status ACCEPTED or UNCHECKED;<br>
     * - if there is no report, a report is generated (without registration in the database) to fix the StatusDate;<br>
     * - sending a notification message to a volunteer based on the results of checking for the difference of 2 days of the year between
     * the current date and day of the year of the report registration date with the status ACCEPTED.<br>
     * - sending a reminder message to the volunteer based on the results of checking for the difference 1 day of the year between
     * the current date and day of the year of the date of registration of the report with the UNCHEKED status.<br>
     * - sending a reminder message to Adopter as a result of checking for the difference 1 day of the year between
     * the current date and day of the year of the report registration date with the status ACCEPTED.<br>
     * The @Scheduled annotation with the parameter (cron = "* * * * * *") activates the method according to the schedule at the moment when
     * specified in the cron parameter = "Second Minute Hour Day Month Year"
     *
     * @see Scheduled
     * @see ExaminationStatus
     * @see Status
     */
    //для проверки рабоспособности cron = "30 * * * * *"
    @Scheduled(cron = "30 * * * * *")
    protected void sendAttentionForDogVolunteerAndAdopterDog() {

        List<AdopterDog> adopters = adopterDogRepository.findAll();
        List<AdopterDog> adoptersWithProbationPeriod = adopters.stream()
                .filter(x -> (x.getState() == PROBATION)
                        || x.getState() == ADDITIONAL_PERIOD_14
                        || x.getState() == ADDITIONAL_PERIOD_30)
                .toList();
        List<ReportDog> reports = reportDogRepository.findAll();
        for (AdopterDog adopter : adoptersWithProbationPeriod) {
            ReportDog report = reports.stream()
                    .filter(x -> (x.getAdopterDog().equals(adopter))
                            && (x.getExamination().equals(ExaminationStatus.ACCEPTED)
                            || x.getExamination() == ExaminationStatus.UNCHECKED
                            || x.getExamination() == ExaminationStatus.REJECTED))
                    .reduce((first, last) -> last)
                    .orElse(new ReportDog(0L,
                            adopter.getStatusDate(),
                            "",
                            "There aren`t reports",
                            ExaminationStatus.REJECTED));
            if (LocalDate.now().getDayOfYear() - report.getReportDate().getDayOfYear() >= 1) {
                //для проверки рабоспособности в условии ниже добавить +3 после LocalDate.now().getDayOfYear()
                if (report.getExamination().equals(ExaminationStatus.UNCHECKED)) {
                    sendMessage(config.getVolunteerChatId(), "Внимание! Необходимо проверить отчет у "
                            + adopter.getFirstName() + " " + adopter.getLastName() + " chatID: " + adopter.getChatId());
                } else if (LocalDate.now().getDayOfYear() - report.getReportDate().getDayOfYear() > 2) {
                    sendMessage(config.getVolunteerChatId(), "Внимание! Усыновитель " + adopter.getFirstName()
                            + " " + adopter.getLastName() + ", username: " + adopter.getUserName()
                            + ", chatId: " + adopter.getChatId() + " уже больше 2 дней не присылает отчеты!");
                }
                sendMessage(adopter.getChatId(), MESSAGE_ATTENTION_REPORT);
            }
        }
    }

    /**
     * The method organizes according to the schedule an automatic check for the availability of reports by the registration period
     * according to the following algorithm:<br>
     * - getting Adopter list with status PROBATION, ADDITIONAL_PERIOD_14, ADDITIONAL_PERIOD_30;<br>
     * - getting for each Adopter from the list of its reports the last report with status ACCEPTED or UNCHECKED;<br>
     * - if there is no report, a report is generated (without registration in the database) to fix the StatusDate;<br>
     * - sending a notification message to a volunteer based on the results of checking for the difference of 2 days of the year between
     * the current date and day of the year of the report registration date with the status ACCEPTED.<br>
     * - sending a reminder message to the volunteer based on the results of checking for the difference 1 day of the year between
     * the current date and day of the year of the date of registration of the report with the UNCHEKED status.<br>
     * - sending a reminder message to Adopter as a result of checking for the difference 1 day of the year between
     * the current date and day of the year of the report registration date with the status ACCEPTED.<br>
     * The @Scheduled annotation with the parameter (cron = "* * * * * *") activates the method according to the schedule at the moment when
     * specified in the cron parameter = "Second Minute Hour Day Month Year"
     *
     * @see Scheduled
     * @see ExaminationStatus
     * @see Status
     */
    //для проверки рабоспособности cron = "30 * * * * *"
    @Scheduled(cron = "30 * * * * *")
    protected void sendAttentionForCatVolunteerAndAdopterCat() {

        List<AdopterCat> adoptersWithProbationPeriod = adopterCatRepository.findAll().stream()
                .filter(x -> (x.getState() == PROBATION)
                        || x.getState() == ADDITIONAL_PERIOD_14
                        || x.getState() == ADDITIONAL_PERIOD_30)
                .toList();
        List<ReportCat> reports = reportCatRepository.findAll();
        for (AdopterCat adopter : adoptersWithProbationPeriod) {
            ReportCat report = reports.stream()
                    .filter(x -> (x.getAdopterCat().equals(adopter))
                            && (x.getExamination().equals(ExaminationStatus.ACCEPTED)
                            || x.getExamination() == ExaminationStatus.UNCHECKED))
                    .reduce((first, last) -> last)
                    .orElse(new ReportCat(0L,
                            adopter.getStatusDate(),
                            "",
                            "There aren`t reports",
                            ExaminationStatus.REJECTED));
            //для проверки рабоспособности в условии ниже добавить +3 после LocalDate.now().getDayOfYear()
            if (LocalDate.now().getDayOfYear() - report.getReportDate().getDayOfYear() >= 1) {
                //для проверки рабоспособности в условии ниже добавить +3 после LocalDate.now().getDayOfYear()
                if (report.getExamination().equals(ExaminationStatus.UNCHECKED)) {
                    sendMessage(config.getVolunteerChatId(), "Внимание! Необходимо проверить отчет у "
                            + adopter.getFirstName() + " " + adopter.getLastName() + " chatID: " + adopter.getChatId());
                } else if (LocalDate.now().getDayOfYear() - report.getReportDate().getDayOfYear() > 2) {
                    sendMessage(config.getVolunteerChatId(), "Внимание! Усыновитель " + adopter.getFirstName()
                            + " " + adopter.getLastName() + ", username: " + adopter.getUserName()
                            + ", chatId: " + adopter.getChatId() + " уже больше 2 дней не присылает отчеты!");
                }
                sendMessage(adopter.getChatId(), MESSAGE_ATTENTION_REPORT);
            }
        }
    }

    /**
     * The method schedules an automatic check for reports with a registration period exceeding:<br>
     * 30 days for adoptive parents with PROBATION or ADDITIONAL_PERIOD_30;<br>
     * 14 days for adoptive parents with the status ADDITIONAL_PERIOD_30.<br>
     * According to the selected list of adopters, the bot sends a message to the volunteer with the text<br>
     * "{@value BotCommands#TAKE_DECISION} userName" and with buttons to select an action for each adopter<br>
     * The @Scheduled annotation with the parameter (cron = "* * * * * *") activates the scheduled method cron = "Second Minute Hour Day Month Year"
     *
     * @see Status
     * @see Scheduled
     * @see Bot#sendMessageWithInlineKeyboard(long, String, String...)
     * @see BotCommands#KEYBOARD_DECISION
     */

    //для проверки рабоспособности cron = "30 * * * * *"
    @Scheduled(cron = "30 * * * * *")
    public void sendFinishListForDogVolunteer() {
        List<AdopterDog> adoptersWithFinishProbationPeriod = adopterDogRepository.findAll().stream()
                .filter(x -> ((x.getState() == PROBATION || x.getState() == ADDITIONAL_PERIOD_30)
                        //для проверки рабоспособности в условии ниже добавить +31 после LocalDate.now().getDayOfYear()
                        && (LocalDate.now().getDayOfYear() - x.getStatusDate().getDayOfYear() > 30))
                        || (x.getState() == ADDITIONAL_PERIOD_14
                        //для проверки рабоспособности в условии ниже добавить +15 после LocalDate.now().getDayOfYear()
                        && (LocalDate.now().getDayOfYear() - x.getStatusDate().getDayOfYear()) > 14))
                .toList();
        for (AdopterDog adopter : adoptersWithFinishProbationPeriod) {
            sendMessageWithInlineKeyboard(config.getVolunteerChatId(),
                    TAKE_DECISION + adopter.getUserName() + ", chatId: " + adopter.getChatId(),
                    KEYBOARD_DECISION);
        }
    }

    /**
     * The method schedules an automatic check for reports with a registration period exceeding:<br>
     * 30 days for adoptive parents with PROBATION or ADDITIONAL_PERIOD_30;<br>
     * 14 days for adoptive parents with status ADDITIONAL_PERIOD_30 :<br>
     * The @Scheduled annotation with the parameter (cron = "* * * * * *") activates the scheduled method cron = "Second Minute Hour Day Month Year"
     *
     * @see Status
     * @see Scheduled
     */

    //для проверки рабоспособности cron = "30 * * * * *"
    @Scheduled(cron = "30 * * * * *")
    void sendFinishListForCatVolunteer() {
        List<AdopterCat> adoptersWithFinishProbationPeriod = adopterCatRepository.findAll().stream()
                .filter(x -> (x.getState() == PROBATION || x.getState() == ADDITIONAL_PERIOD_30)
                        //для проверки рабоспособности в условии ниже добавить +31 после LocalDate.now().getDayOfYear()
                        && (LocalDate.now().getDayOfYear() - x.getStatusDate().getDayOfYear() + 30 > 30)
                        || (x.getState() == ADDITIONAL_PERIOD_14
                        //для проверки рабоспособности в условии ниже добавить +15 после LocalDate.now().getDayOfYear()
                        && LocalDate.now().getDayOfYear() - x.getStatusDate().getDayOfYear() + 30 > 14))
                .toList();
        for (AdopterCat adopter : adoptersWithFinishProbationPeriod) {
            sendMessageWithInlineKeyboard(config.getVolunteerChatId(),
                    TAKE_DECISION + adopter.getUserName() + ", chatId: " + adopter.getChatId(),
                    KEYBOARD_DECISION);

        }
    }

    /**
     * The method splits the text of the message sent to the volunteer into 2 components, gets the userName, finds the chatId for
     * of this user and updates his status and status date on the date of the call to the method <br>
     *
     * @param botReplies  notification message to user about status change
     * @param messageText the text of the message from which the volunteer pressed the button in the format<br>
     *                    "{@value BotCommands#TAKE_DECISION} userName" to get userName
     * @param status      the value of the status to which the replacement will be
     * @see AdopterDog#setState(Status)
     * @see AdopterDog#setStatusDate(LocalDate)
     */
    public void changeDogAdopterStatus(String botReplies, String messageText, Status status) {

        Long chatId = Long.valueOf(messageText.split("chatId: ")[1]);
        AdopterDog adopterDog = adopterDogService.get(chatId);
        adopterDog.setState(status);
        //для проверки рабоспособности изменения даты использовать параметр LocalDate.now().minusDays(5)
        adopterDog.setStatusDate(LocalDate.now());
        sendMessage(chatId, botReplies);
        adopterDogRepository.save(adopterDog);
        adopterDogService.update(adopterDog);
        sendMessage(config.getVolunteerChatId(), "Для пользователя UserName: " + adopterDog.getUserName() +
                ", ChatId: " + chatId + " выполнено:\n" + botReplies);
    }

    /**
     * The method splits the text of the message sent to the volunteer into 2 components, gets the userName, finds the chatId for
     * of this user and updates his status and status date on the date of the call to the method <br>
     *
     * @param botReplies  notification message to user about status change
     * @param messageText the text of the message from which the volunteer pressed the button in the format<br>
     *                    "{@value BotCommands#TAKE_DECISION} userName" to get userName
     * @param status      the value of the status to which the replacement will be
     * @see AdopterCat#setState(Status)
     * @see AdopterCat#setStatusDate(LocalDate)
     */
    public void changeCatAdopterStatus(String botReplies, String messageText, Status status) {
        Long chatId = Long.valueOf(messageText.split("chatId: ")[1]);
        AdopterCat adopterCat = adopterCatService.get(chatId);
        adopterCat.setState(status);
        //для проверки работоспособности изменения даты использовать параметр LocalDate.now().minusDays(5)
        adopterCat.setStatusDate(LocalDate.now());
        sendMessage(chatId, botReplies);
        adopterCatRepository.save(adopterCat);
        adopterCatService.update(adopterCat);
        sendMessage(config.getVolunteerChatId(), "Для пользователя" + chatId + "выполнено:" + botReplies);
    }

    /**
     * Method for sending message to bot user using Telegram API
     *
     * @param chatId     user ID
     * @param textToSend sent text
     * @throws IOException
     */
    public static void sendToTelegram(Long chatId, String textToSend) {

        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
        String apiToken = resource.getString("botToken");

        urlString = String.format(urlString, apiToken, chatId, textToSend);

        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
