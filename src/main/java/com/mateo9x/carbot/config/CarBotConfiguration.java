package com.mateo9x.carbot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class CarBotConfiguration {

    public static String getToken() throws IOException {
        File file = new ClassPathResource("token.json").getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Token token = objectMapper.readValue(file, Token.class);
            return token.getToken();
        } catch (IOException e) {
            throw new RuntimeException("Nie udało pobrać się tokenu uwierzytelniającego dla bota");
        }
    }

    @Data
    private static class Token {
        String token;
    }
}
