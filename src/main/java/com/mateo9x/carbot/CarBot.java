package com.mateo9x.carbot;

import com.mateo9x.carbot.config.CarBotConfiguration;
import com.mateo9x.carbot.event.MessageListener;
import com.mateo9x.carbot.exception.AppStartException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.IOException;

@Slf4j
@Getter
public class CarBot {

    private JDA jda;

    void start() {
        try {
            this.jda = JDABuilder.createDefault(CarBotConfiguration.getToken())
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(new MessageListener())
                    .setActivity(Activity.listening("uczestników czatu"))
                    .build().awaitReady();
        } catch (InterruptedException | IOException e) {
            log.error(e.getMessage(), e);
            throw new AppStartException("Start bota nie powiódł się");
        }
    }
}
