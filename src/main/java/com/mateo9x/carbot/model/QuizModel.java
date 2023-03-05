package com.mateo9x.carbot.model;

import lombok.Getter;

import java.util.List;

@Getter
public class QuizModel {
    private String question;
    private List<String> answers;
}
