package com.team4.happydogbot.replies;

import com.team4.happydogbot.service.Bot;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.team4.happydogbot.constants.BotCommands.*;
import static com.team4.happydogbot.constants.BotReplies.*;

/**
 * The class contains maps in which commands correspond to the bot's reaction to these commands
 *
 * @see com.team4.happydogbot.constants.BotCommands
 * @see com.team4.happydogbot.constants.BotReplies
 */
public class Reply {

    private Bot bot;

    public Reply(Bot bot) {
        this.bot = bot;
    }

    /**
     * In the map, the key is the command, the value is the reaction to the command
     * In this command reaction map, if the user has selected a cat
     */
    public Map<String, Consumer<Long>> catReplies = new HashMap<>();

    {
        //Этап 0
        catReplies.put(PET_INFO_CMD, chatId -> bot.sendMessageWithInlineKeyboard(chatId, MESSAGE_TEXT_PET_INFO, KEYBOARD_CAT_ADOPT));

        //Этап 1
        catReplies.put(SHELTER_ABOUT_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_CAT_SHELTER_ABOUT));
        catReplies.put(SHELTER_SAFETY_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_CAT_SHELTER_SAFETY));
        catReplies.put(SHELTER_SCHEDULE_ADDRESS_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_CAT_SHELTER_SCHEDULE_ADDRESS));
        catReplies.put(CAR_PASS_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_CAT_CAR_PASS));
        catReplies.put(SEND_CONTACT_CMD, chatId -> bot.sendMessageWithContactKeyboard(chatId));
        catReplies.put(BACK_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_TEXT_CHOOSE_ACTION, bot.replyKeyboardBottom()));

        //Этап 2
        catReplies.put(PET_RULES_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_CAT_RULES));
        catReplies.put(PET_DOCS_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_CAT_DOCS));
        catReplies.put(PET_TRANSPORT_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_CAT_TRANSPORT));
        catReplies.put(PET_REFUSAL_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_CAT_REFUSAL));
        catReplies.put(PET_HOUSE_CMD, chatId -> bot.sendMessageWithInlineKeyboard(chatId, MESSAGE_CAT_HOUSE_CHOOSE, KEYBOARD_CAT_HOUSE));

        catReplies.put(PET_HOUSE_FOR_KITTY_CMD, chatId -> bot.sendDocument(chatId, URL_CAT_HOUSE_KITTY));
        catReplies.put(PET_HOUSE_FOR_ADULT_CMD, chatId -> bot.sendDocument(chatId, URL_CAT_HOUSE_ADULT));
        catReplies.put(PET_HOUSE_FOR_SICK_CMD, chatId -> bot.sendDocument(chatId, URL_CAT_HOUSE_SICK));

        // Этап 3
        catReplies.put(REPORT_FORM, chatId -> bot.sendMessageWithInlineKeyboard(chatId, MESSAGE_TEXT_REPORT_FORM, REPORT_EXAMPLE, SEND_REPORT));
        catReplies.put(REPORT_EXAMPLE, chatId -> bot.sendPhotoWithCaptionWithInlineKeyboard(chatId, MESSAGE_CAT_REPORT_EXAMPLE, URL_CAT_REPORT_EXAMPLE_PHOTO, SEND_REPORT));
    }

    /**
     * In the map, the key is the command, the value is the reaction to the command
     * In this command reaction map, if the user has selected a dog,
     * and reactions that do not depend on the choice of dog or cat
     */
    public Map<String, Consumer<Long>> dogReplies = new HashMap<>();

    {
        //Этап 0
        dogReplies.put(SHELTER_CHOOSE, chatId -> bot.sendMessage(chatId, MESSAGE_TEXT_CHOOSE_SHELTER, bot.replyKeyboardShelter()));
        dogReplies.put(SHELTER_CAT, chatId -> {
            bot.sendMessage(chatId, MESSAGE_TEXT_CHOOSE_ACTION, bot.replyKeyboardBottom());
            bot.changeUserStatusOfShelter(chatId, false);
        });
        dogReplies.put(SHELTER_DOG, chatId -> {
            bot.sendMessage(chatId, MESSAGE_TEXT_CHOOSE_ACTION, bot.replyKeyboardBottom());
            bot.changeUserStatusOfShelter(chatId, true);
        });
        dogReplies.put(SHELTER_INFO_CMD, chatId -> bot.sendMessageWithInlineKeyboard(chatId, MESSAGE_TEXT_SHELTER_INFO, KEYBOARD_SHELTER_ABOUT));
        dogReplies.put(PET_INFO_CMD, chatId -> bot.sendMessageWithInlineKeyboard(chatId, MESSAGE_TEXT_PET_INFO, KEYBOARD_DOG_ADOPT));
        dogReplies.put(SEND_REPORT_CMD, chatId -> bot.sendMessageWithInlineKeyboard(chatId, MESSAGE_TEXT_REPORT, KEYBOARD_REPORT));
        dogReplies.put(FINISH_VOLUNTEER_CMD, chatId -> {
            bot.findAndRemoveRequestFromUser(chatId);
            bot.sendMessage(chatId, MESSAGE_TEXT_TALK_ENDED);
        });
        // Если юзер нажал кнопку Закончить разговор с волонтером,
        // то удаляем последнее сообщение из мапы - т.е. выходим из состояния разговора с волонтером,
        // выводим сообщение, что разговор с волонтером закончен

        //Этап 1
        dogReplies.put(SHELTER_ABOUT_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_DOG_SHELTER_ABOUT));
        dogReplies.put(SHELTER_SAFETY_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_DOG_SHELTER_SAFETY));
        dogReplies.put(SHELTER_SCHEDULE_ADDRESS_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_DOG_SHELTER_SCHEDULE_ADDRESS));
        dogReplies.put(CAR_PASS_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_DOG_CAR_PASS));
        dogReplies.put(SEND_CONTACT_CMD, chatId -> bot.sendMessageWithContactKeyboard(chatId));
        dogReplies.put(BACK_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_TEXT_CHOOSE_ACTION, bot.replyKeyboardBottom()));

        //Этап 2
        dogReplies.put(PET_RULES_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_DOG_RULES));
        dogReplies.put(PET_DOCS_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_DOG_DOCS));
        dogReplies.put(PET_TRANSPORT_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_DOG_TRANSPORT));
        dogReplies.put(PET_ADVICES_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_DOG_ADVICES));
        dogReplies.put(PET_CYNOLOGISTS_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_DOG_CYNOLOGISTS));
        dogReplies.put(PET_REFUSAL_CMD, chatId -> bot.sendMessage(chatId, MESSAGE_DOG_REFUSAL));
        dogReplies.put(PET_HOUSE_CMD, chatId -> bot.sendMessageWithInlineKeyboard(chatId, MESSAGE_TEXT_DOG_HOUSE_CHOOSE, KEYBOARD_DOG_HOUSE));

        dogReplies.put(PET_HOUSE_FOR_PUPPY_CMD, chatId -> bot.sendDocument(chatId, URL_DOG_HOUSE_PUPPY));
        dogReplies.put(PET_HOUSE_FOR_ADULT_CMD, chatId -> bot.sendDocument(chatId, URL_DOG_HOUSE_ADULT));
        dogReplies.put(PET_HOUSE_FOR_SICK_CMD, chatId -> bot.sendDocument(chatId, URL_DOG_HOUSE_SICK));

        // Этап 3
        dogReplies.put(REPORT_FORM, chatId -> bot.sendMessageWithInlineKeyboard(chatId, MESSAGE_TEXT_REPORT_FORM, REPORT_EXAMPLE, SEND_REPORT));
        dogReplies.put(REPORT_EXAMPLE, chatId -> bot.sendPhotoWithCaptionWithInlineKeyboard(chatId, MESSAGE_DOG_REPORT_EXAMPLE, URL_DOG_REPORT_EXAMPLE_PHOTO, SEND_REPORT));
    }
}
