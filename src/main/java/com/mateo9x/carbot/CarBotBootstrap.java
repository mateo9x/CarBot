package com.mateo9x.carbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarBotBootstrap {

    public static void main(String[] args) {
        CarBot carBot = new CarBot();
        carBot.start();
        SpringApplication.run(CarBotBootstrap.class, args);
    }

}
