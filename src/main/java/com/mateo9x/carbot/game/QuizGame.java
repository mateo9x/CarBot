package com.mateo9x.carbot.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateo9x.carbot.CarBot;
import com.mateo9x.carbot.config.Language;
import com.mateo9x.carbot.message.MessageSource;
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

    public static void startQuiz(CarBot carBot, Message message) {
        if (!isAnswer(message.getContentDisplay())) {
            prepareNewQuestion(carBot.getLanguage(), message);
        } else {
            String answer = message.getContentDisplay().substring(6);
            checkAnswer(carBot.getLanguage(), message, answer);
        }
    }

    private static boolean isAnswer(String text) {
        return text.startsWith("/quiz") && text.length() > 5;
    }

    private static void prepareNewQuestion(Language language, Message message) {
        String quiz = generateQuiz(language);
        String header = MessageSource.getMessage(language, QUIZ_START_PROPERTY);
        String responseTip = MessageSource.getMessage(language, QUIZ_RESPONSE_TIP_PROPERTY);
        message.getChannel().sendMessage(header + "\n\n" + quiz + "\n\n" + responseTip)
                .complete();
    }

    private static void checkAnswer(Language language, Message message, String answer) {
        if (quizSaved == null) {
            handleQuizDoesntExist(language, message);
        } else {
            if (quizSaved.getAnswers().contains(answer)) {
                handleQuizCorrectAnswer(language, message);
            } else {
                handleQuizNotCorrectAnswer(language, message);
            }
        }
    }

    private static String generateQuiz(Language language) {
        List<QuizModel> quizList = getQuizList(language);
        Random random = new Random();
        int number = random.nextInt(quizList.size());
        QuizModel quizModel = quizList.get(number);
        saveQuiz(quizModel);
        return quizModel.getQuestion();
    }

    private static void handleQuizDoesntExist(Language language, Message message) {
        String response = MessageSource.getMessage(language, QUIZ_NOT_FOUND_PROPERTY);
        message.getChannel().sendMessage(response)
                .complete();
    }

    private static void handleQuizCorrectAnswer(Language language, Message message) {
        String response = MessageSource.getMessage(language, QUIZ_CORRECT_PROPERTY);
        quizSaved = null;
        message.addReaction(Emoji.fromFormatted("❤"))
                .complete();
        message.getChannel().sendMessage(response)
                .complete();
    }

    private static void handleQuizNotCorrectAnswer(Language language, Message message) {
        String response = MessageSource.getMessage(language, QUIZ_NOT_CORRECT_PROPERTY);
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
