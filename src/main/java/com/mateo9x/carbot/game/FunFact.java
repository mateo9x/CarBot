package com.mateo9x.carbot.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateo9x.carbot.model.FunFactModel;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FunFact {

    private static final List<FunFactModel> funFacts = prepareFunFacts();

    public static void drawFunFact(Message message) {
        Random random = new Random();
        int number = random.nextInt(funFacts.size());
        FunFactModel funFact = funFacts.get(number);
        message.getChannel().sendMessage("Ciekawostka: \n" + funFact.getText()).complete();
    }

    private static List<FunFactModel> prepareFunFacts() {
        try {
            InputStream inputStream = new ClassPathResource("funFacts.json").getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, new TypeReference<>() {});
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
