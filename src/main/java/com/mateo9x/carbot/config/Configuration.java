package com.mateo9x.carbot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public class Configuration {

    public static Config getConfiguration() throws IOException {
        InputStream inputStream = new ClassPathResource("configuration.json").getInputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Config config = objectMapper.readValue(inputStream, Config.class);
            ConfigurationValidator.validateConfig(config);
            return config;
        } catch (IOException e) {
            throw new ConfigurationException("Couldn't parse configuration.json");
        }
    }

    @Data
    public static class Config {
        private String token;
        private String language;
    }
}

class ConfigurationException extends RuntimeException {
    public ConfigurationException(String message) {
        super(message);
    }
}
