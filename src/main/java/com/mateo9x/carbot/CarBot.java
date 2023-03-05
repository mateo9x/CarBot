package com.mateo9x.carbot;

import com.mateo9x.carbot.config.Configuration;
import com.mateo9x.carbot.config.Language;
import com.mateo9x.carbot.event.MessageListener;
import com.mateo9x.carbot.exception.AppStartException;
import com.mateo9x.carbot.message.MessageSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.IOException;

@Slf4j
@Data
public class CarBot {

    private static final String ACTIVITY_LABEL_PROPERTY = "activity.label";
    private JDA jda;
    private MessageSource messageSource;

    void start() {
        try {
            Configuration.Config config = Configuration.getConfiguration();
            this.messageSource = new MessageSource(Language.getByValue(config.getLanguage()));
            this.jda = JDABuilder.createDefault(config.getToken())
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(new MessageListener(this))
                    .setActivity(Activity.listening(this.messageSource.getMessage(ACTIVITY_LABEL_PROPERTY)))
                    .build().awaitReady();
        } catch (InterruptedException | IOException e) {
            log.error(e.getMessage(), e);
            throw new AppStartException("Couldn't launch bot");
        }
    }
}
