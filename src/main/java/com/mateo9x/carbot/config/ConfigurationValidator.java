package com.mateo9x.carbot.config;

import static org.apache.logging.log4j.util.Strings.isBlank;

public class ConfigurationValidator {

    public static void validateConfig(Configuration.Config config) {
        if (isBlank(config.getToken())) {
            throw new ConfigurationException("Token not found");
        }
        if (isBlank(config.getLanguage()) || (!isBlank(config.getLanguage()) && Language.getByValue(config.getLanguage()) == null)) {
            throw new ConfigurationException("Language not found (available en/pl)");
        }
    }
}
