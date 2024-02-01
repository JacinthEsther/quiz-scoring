package com.jacinthsolution.quizscoring.services;

import com.jacinthsolution.quizscoring.entities.QuestionType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizScoringService {

    public int scoreQuiz(List<String> userAnswers, List<String> correctAnswers, QuestionType questionType) {
        // Implement scoring logic based on question type
        int score = 0;

        switch (questionType) {
            case MULTIPLE_CHOICE:
                score = scoreMultipleChoice(userAnswers, correctAnswers);
                break;
            case TRUE_FALSE:
                score = scoreTrueFalse(userAnswers, correctAnswers);
                break;
            // Add cases for other question types
        }

        return score;
    }

    private int scoreMultipleChoice(List<String> userAnswers, List<String> correctAnswers) {
        // Implement scoring logic for multiple-choice questions
        // Example: each correct answer contributes 1 point
        return (int) userAnswers.stream().filter(correctAnswers::contains).count();
    }

    private int scoreTrueFalse(List<String> userAnswers, List<String> correctAnswers) {
        // Implement scoring logic for true/false questions
        // Example: each correct answer contributes 1 point
        return (int) userAnswers.stream().filter(correctAnswers::contains).count();
    }
}

