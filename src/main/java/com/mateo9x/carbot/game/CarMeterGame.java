package com.mateo9x.carbot.game;

import com.mateo9x.carbot.CarBot;
import com.mateo9x.carbot.config.Language;
import com.mateo9x.carbot.message.MessageSource;
import net.dv8tion.jda.api.entities.Message;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CarMeterGame {

    private static final String CAR_METER_PROPERTY = "car.meter";
    private static final String CAR_METER_SELECTED_BRAND_PROPERTY = "car.meter.selected.brand";
    private static final String CAR_METER_VERY_LOW_PROPERTY = "car.meter.very.low";
    private static final String CAR_METER_LOW_PROPERTY = "car.meter.low";
    private static final String CAR_METER_MEDIUM_PROPERTY = "car.meter.medium";
    private static final String CAR_METER_HIGH_PROPERTY = "car.meter.high";
    private static final String CAR_METER_RESULT_PROPERTY = "car.meter.result";
    private static final List<String> carBrands;

    static {
        carBrands = Arrays.asList("Alfa Romeo", "Aston Martin", "Audi", "BMW", "Mercedes-Benz", "Hyundai", "Nissan", "Land Rover", "KIA", "Honda",
                "Pagani", "Koenigseg", "Ferrari", "Lamborghini", "Bugatti", "Chevrolet", "Chrysler", "Dacia", "Daewoo", "Ford", "Jaguar", "Jeep",
                "Mitsubishi", "Opel", "Peugeot", "Citroen", "Fiat", "Lexus", "Maserati", "Mazda", "MINI", "Skoda", "Rolls-Royce", "Seat", "Volkswagen");
    }

    public static void start(CarBot carBot, Message message) {
        Random random = new Random();
        int percentageAsBrandEnthusiastic = random.nextInt(0, 100);
        String label = MessageSource.getMessage(carBot.getLanguage(), CAR_METER_PROPERTY);
        String carBrandSelected = carBrands.get(random.nextInt(carBrands.size()));
        String selectedBrandText = MessageSource.getMessage(carBot.getLanguage(), CAR_METER_SELECTED_BRAND_PROPERTY);
        String percentageText = getPercentageText(carBot.getLanguage(), percentageAsBrandEnthusiastic);
        String result = MessageSource.getMessage(carBot.getLanguage(), CAR_METER_RESULT_PROPERTY);
        message.getChannel().sendMessage(label + "\n\n" + selectedBrandText + " *" + carBrandSelected + "*\n" + percentageText + "\n" + result + " *" + percentageAsBrandEnthusiastic + "%*")
                .complete();
    }

    private static String getPercentageText(Language language, int percentage) {
        if (percentage < 25) {
            return MessageSource.getMessage(language, CAR_METER_VERY_LOW_PROPERTY);
        } else if (percentage < 50) {
            return MessageSource.getMessage(language, CAR_METER_LOW_PROPERTY);
        } else if (percentage < 75) {
            return MessageSource.getMessage(language, CAR_METER_MEDIUM_PROPERTY);
        } else {
            return MessageSource.getMessage(language, CAR_METER_HIGH_PROPERTY);
        }
    }
}
