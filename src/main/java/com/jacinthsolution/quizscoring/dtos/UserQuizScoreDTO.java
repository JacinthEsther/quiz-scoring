package com.jacinthsolution.quizscoring.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserQuizScoreDTO {
    private List<Long> quizId;

    private int score;
}
