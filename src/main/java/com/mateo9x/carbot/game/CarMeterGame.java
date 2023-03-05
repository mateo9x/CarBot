package com.mateo9x.carbot.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateo9x.carbot.CarBot;
import com.mateo9x.carbot.model.CarMeterModel;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CarMeterGame {

    private static final String CAR_METER_PROPERTY = "car.meter";
    private static final String CAR_METER_SELECTED_BRAND_PROPERTY = "car.meter.selected.brand";
    private static final String CAR_METER_VERY_LOW_PROPERTY = "car.meter.very.low";
    private static final String CAR_METER_LOW_PROPERTY = "car.meter.low";
    private static final String CAR_METER_MEDIUM_PROPERTY = "car.meter.medium";
    private static final String CAR_METER_HIGH_PROPERTY = "car.meter.high";
    private static final String CAR_METER_RESULT_PROPERTY = "car.meter.result";
    private static final List<String> carBrands = getCarBrands();

    public static void start(CarBot carBot, Message message) {
        Random random = new Random();
        int percentageAsBrandEnthusiastic = random.nextInt(0, 100);
        String label = carBot.getMessageSource().getMessage(CAR_METER_PROPERTY);
        String carBrandSelected = carBrands.get(random.nextInt(carBrands.size()));
        String selectedBrandText = carBot.getMessageSource().getMessage(CAR_METER_SELECTED_BRAND_PROPERTY);
        String percentageText = getPercentageText(carBot, percentageAsBrandEnthusiastic);
        String result = carBot.getMessageSource().getMessage(CAR_METER_RESULT_PROPERTY);
        message.getChannel().sendMessage(label + "\n\n" + selectedBrandText + " *" + carBrandSelected + "*\n" + percentageText + "\n" + result + " *" + percentageAsBrandEnthusiastic + "%*")
                .complete();
    }

    private static String getPercentageText(CarBot carBot, int percentage) {
        if (percentage < 25) {
            return carBot.getMessageSource().getMessage(CAR_METER_VERY_LOW_PROPERTY);
        } else if (percentage < 50) {
            return carBot.getMessageSource().getMessage(CAR_METER_LOW_PROPERTY);
        } else if (percentage < 75) {
            return carBot.getMessageSource().getMessage(CAR_METER_MEDIUM_PROPERTY);
        } else {
            return carBot.getMessageSource().getMessage(CAR_METER_HIGH_PROPERTY);
        }
    }

    private static List<String> getCarBrands() {
        try {
            InputStream inputStream = new ClassPathResource("carMeter/carBrands.json").getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            CarMeterModel carMeterModel = objectMapper.readValue(inputStream, CarMeterModel.class);
            return carMeterModel.getBrands();
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
