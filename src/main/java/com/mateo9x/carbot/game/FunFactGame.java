package com.mateo9x.carbot.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateo9x.carbot.CarBot;
import com.mateo9x.carbot.config.Language;
import com.mateo9x.carbot.message.MessageSource;
import com.mateo9x.carbot.model.FunFactModel;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FunFactGame {

    private static final String FUN_FACT_PROPERTY = "fun.fact";

    public static void drawFunFact(CarBot carBot, Message message) {
        final List<FunFactModel> funFacts = getFunFacts(carBot.getLanguage());
        Random random = new Random();
        int number = random.nextInt(funFacts.size());
        FunFactModel funFact = funFacts.get(number);
        String header = MessageSource.getMessage(carBot.getLanguage(), FUN_FACT_PROPERTY);
        message.getChannel().sendMessage(header + "\n\n" + funFact.getText())
                .complete();
    }

    private static List<FunFactModel> getFunFacts(Language language) {
        try {
            InputStream inputStream = new ClassPathResource("funFacts/" + language.value + "/funFacts.json").getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, new TypeReference<>() {});
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
