package com.jacinthsolution.quizscoring.dtos;

import lombok.Data;

@Data
public class UserQuizScoreDTO {
    private Long quizId;

    private int score;
}
