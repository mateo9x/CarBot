package com.mateo9x.carbot.handler;

import com.mateo9x.carbot.CarBot;
import com.mateo9x.carbot.config.Configuration;
import com.mateo9x.carbot.game.CarMeterGame;
import com.mateo9x.carbot.game.GameType;
import com.mateo9x.carbot.game.FunFactGame;
import com.mateo9x.carbot.game.QuizGame;
import com.mateo9x.carbot.validator.ForbiddenWordValidator;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;

import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Slf4j
public class MessageHandler {

    private static final String BAD_LANGUAGE_RESPONSE_PROPERTY = "bad.language.response";

    public static void handleMessage(CarBot carBot, Message message) {
        Boolean allowForbiddenLanguage = null;
        try {
            allowForbiddenLanguage = Configuration.getConfiguration().getAllowForbiddenLanguage();
        } catch (IOException e) {
            log.error("Couldn't get configuration");
        }
        if (!doesServerAllowForbidenLanguage(allowForbiddenLanguage) && doesMessageContainsForbiddenWord(carBot, message.getContentDisplay())) {
            message.addReaction(Emoji.fromFormatted("\uD83D\uDE21"))
                    .complete();
            message.getChannel().sendMessage(carBot.getMessageSource().getMessage(BAD_LANGUAGE_RESPONSE_PROPERTY))
                    .complete();
        } else {
            selectProperGame(carBot, message);
        }
    }

    private static boolean doesMessageContainsForbiddenWord(CarBot carBot, String text) {
        return ForbiddenWordValidator.doesTextContainsForbiddenWord(carBot, text.toLowerCase());
    }

    private static void selectProperGame(CarBot carBot, Message message) {
        if (isMessageForBot(message.getContentDisplay())) {
            switch (GameType.getByValue(getPrefix(message.getContentDisplay()))) {
                case FunFact -> FunFactGame.draw(carBot, message);
                case Quiz -> QuizGame.start(carBot, message);
                case CarMeter -> CarMeterGame.start(carBot, message);
            }
        }
    }

    private static boolean isMessageForBot(String message) {
        return isNotBlank(message) && message.startsWith("/");
    }

    private static String getPrefix(String text) {
        if (text.contains(" ")) {
            return text.substring(0, text.indexOf(" "));
        }
        return text;
    }

    private static boolean doesServerAllowForbidenLanguage(Boolean allowForbiddenLanguage) {
        return allowForbiddenLanguage != null && allowForbiddenLanguage;
    }
}
