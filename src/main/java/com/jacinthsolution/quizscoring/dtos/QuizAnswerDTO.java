package com.jacinthsolution.quizscoring.dtos;

import lombok.Data;

import java.util.List;

@Data
public class QuizAnswerDTO {
    private List<String> possibleAnswers;
    private String correctAnswer;
}