package com.jacinthsolution.quizscoring.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizSubmissionDTO {

    @NotNull(message = "Quiz ID cannot be null")
    private Long quizId;

    @NotEmpty(message = "User answer cannot be empty")
    private String userAnswer;


}
