package com.jacinthsolution.quizscoring.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class QuizQuestionDTO {
    private String question;

    @Pattern(regexp = "^(?i)(MULTIPLE_CHOICE|TRUE_FALSE)$", message = "Question type should be 'MULTIPLE_CHOICE' or 'TRUE_FALSE'")
    private String questionType;

    @Pattern(regexp = "^(?i)(easy|medium|hard)$", message = "Difficulty Level should be 'Easy' or 'Medium' or 'Hard'")
    private String difficultyLevel;

}
