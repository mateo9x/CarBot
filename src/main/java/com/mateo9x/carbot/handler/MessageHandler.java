package com.mateo9x.carbot.handler;

import com.mateo9x.carbot.CarBot;
import com.mateo9x.carbot.config.Language;
import com.mateo9x.carbot.game.CarMeterGame;
import com.mateo9x.carbot.game.GameType;
import com.mateo9x.carbot.game.FunFactGame;
import com.mateo9x.carbot.game.QuizGame;
import com.mateo9x.carbot.message.MessageSource;
import com.mateo9x.carbot.validator.ForbiddenWordValidator;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Slf4j
public class MessageHandler {

    private static final String BAD_LANGUAGE_RESPONSE_PROPERTY = "bad.language.response";

    public static void handleMessage(CarBot carBot, Message message) {
        if (doesMessageContainsForbiddenWord(carBot.getLanguage(), message.getContentDisplay())) {
            message.getChannel().sendMessage(MessageSource.getMessage(carBot.getLanguage(), BAD_LANGUAGE_RESPONSE_PROPERTY))
                    .complete();
        } else {
            selectProperGame(carBot, message);
        }
    }

    private static boolean doesMessageContainsForbiddenWord(Language language, String text) {
        return ForbiddenWordValidator.doesTextContainsForbiddenWord(language, text.toLowerCase());
    }

    private static void selectProperGame(CarBot carBot, Message message) {
        if (isMessageForBot(message.getContentDisplay())) {
            switch (GameType.getByValue(getPrefix(message.getContentDisplay()))) {
                case FunFact -> FunFactGame.drawFunFact(carBot, message);
                case Quiz -> QuizGame.startQuiz(carBot, message);
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
}
