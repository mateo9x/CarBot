package com.mateo9x.carbot.handler;

import com.mateo9x.carbot.enums.Response;
import com.mateo9x.carbot.validator.ForbiddenWordDictionary;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Slf4j
public class MessageHandler {

    public static void handleMessage(Message message) {
        if (doesMessageContainsForbiddenWord(message.getContentDisplay())) {
            message.getChannel().sendMessage("Na tym serwerze stosujemy się do polityki używania poprawnej i nie wulgarnej polszczyzny :smile:")
                    .complete();
        } else {
            selectProperResponse(message);
        }
    }

    private static boolean doesMessageContainsForbiddenWord(String text) {
        return ForbiddenWordDictionary.forbiddenWordList.contains(text.toLowerCase());
    }

    private static void selectProperResponse(Message message) {
        if (isMessageForBot(message.getContentDisplay())) {
            switch (Response.getResponseByValue(message.getContentDisplay())) {
                case FunFact -> {
                    message.getChannel().sendMessage("Ciekawostka:").complete();
                }
                case Quiz -> {
                    message.getChannel().sendMessage("Czas na quiz:").complete();
                }
            }
        }
    }

    private static boolean isMessageForBot(String message) {
        return isNotBlank(message) && message.startsWith("/");
    }
}
