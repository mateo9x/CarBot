package com.mateo9x.carbot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public class CarBotConfiguration {

    public static String getToken() throws IOException {
        InputStream inputStream = new ClassPathResource("token.json").getInputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Token token = objectMapper.readValue(inputStream, Token.class);
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
