package com.jacinthsolution.quizscoring.dtos;

import lombok.Data;

import java.util.List;

@Data
public class QuizAnswerDTO {
    private List<String> answers;
    private String correctAnswer;

}