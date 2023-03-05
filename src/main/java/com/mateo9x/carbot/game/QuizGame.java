package com.mateo9x.carbot.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateo9x.carbot.CarBot;
import com.mateo9x.carbot.config.Language;
import com.mateo9x.carbot.model.QuizModel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizGame {

    private static final String QUIZ_START_PROPERTY = "quiz.start";
    private static final String QUIZ_NOT_FOUND_PROPERTY = "quiz.not.found.tip";
    private static final String QUIZ_CORRECT_PROPERTY = "quiz.correct.tip";
    private static final String QUIZ_NOT_CORRECT_PROPERTY = "quiz.not.correct.tip";
    private static final String QUIZ_RESPONSE_TIP_PROPERTY = "quiz.response.tip";
    private static QuizModel quizSaved = null;

    public static void start(CarBot carBot, Message message) {
        if (!isAnswer(message.getContentDisplay())) {
            prepareNewQuestion(carBot, message);
        } else {
            String answer = message.getContentDisplay().substring(6);
            checkAnswer(carBot, message, answer);
        }
    }

    private static boolean isAnswer(String text) {
        return text.startsWith("/quiz") && text.length() > 5;
    }

    private static void prepareNewQuestion(CarBot carBot, Message message) {
        String quiz = generateQuiz(carBot);
        String header = carBot.getMessageSource().getMessage(QUIZ_START_PROPERTY);
        String responseTip = carBot.getMessageSource().getMessage(QUIZ_RESPONSE_TIP_PROPERTY);
        message.getChannel().sendMessage(header + "\n\n" + quiz + "\n\n" + responseTip)
                .complete();
    }

    private static void checkAnswer(CarBot carBot, Message message, String answer) {
        if (quizSaved == null) {
            handleQuizDoesntExist(carBot, message);
        } else {
            if (quizSaved.getAnswers().contains(answer)) {
                handleQuizCorrectAnswer(carBot, message);
            } else {
                handleQuizNotCorrectAnswer(carBot, message);
            }
        }
    }

    private static String generateQuiz(CarBot carBot) {
        List<QuizModel> quizList = getQuizList(carBot.getMessageSource().getLanguage());
        Random random = new Random();
        int number = random.nextInt(quizList.size());
        QuizModel quizModel = quizList.get(number);
        saveQuiz(quizModel);
        return quizModel.getQuestion();
    }

    private static void handleQuizDoesntExist(CarBot carBot, Message message) {
        String response = carBot.getMessageSource().getMessage(QUIZ_NOT_FOUND_PROPERTY);
        message.getChannel().sendMessage(response)
                .complete();
    }

    private static void handleQuizCorrectAnswer(CarBot carBot, Message message) {
        String response = carBot.getMessageSource().getMessage(QUIZ_CORRECT_PROPERTY);
        quizSaved = null;
        message.addReaction(Emoji.fromFormatted("❤"))
                .complete();
        message.getChannel().sendMessage(response)
                .complete();
    }

    private static void handleQuizNotCorrectAnswer(CarBot carBot, Message message) {
        String response = carBot.getMessageSource().getMessage(QUIZ_NOT_CORRECT_PROPERTY);
        message.getChannel().sendMessage(response)
                .complete();
    }

    private static void saveQuiz(QuizModel quizModel) {
        quizSaved = quizModel;
    }

    private static List<QuizModel> getQuizList(Language language) {
        try {
            InputStream inputStream = new ClassPathResource("quiz/" + language.value + "/quiz.json").getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
