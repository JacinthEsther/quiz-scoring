package com.jacinthsolution.quizscoring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizScoreDTO {
    private String question;
    private String correctAnswer;
    private int score;
}
