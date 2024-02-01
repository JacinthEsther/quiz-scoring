package com.jacinthsolution.quizscoring.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizSubmissionDTO {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Quiz ID cannot be null")
    private Long quizId;

    @NotEmpty(message = "User answers cannot be empty")
    private List<String> userAnswers;
}
