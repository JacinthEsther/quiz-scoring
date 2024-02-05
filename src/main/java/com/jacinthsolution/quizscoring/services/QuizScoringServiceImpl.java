package com.jacinthsolution.quizscoring.services;

import com.jacinthsolution.quizscoring.entities.QuestionType;
import org.springframework.stereotype.Service;

@Service
public class QuizScoringServiceImpl implements QuizScoringService {

    public int scoreQuiz(String userAnswer, String correctAnswer, QuestionType questionType) {
        return switch (questionType) {
            case MULTIPLE_CHOICE -> scoreMultipleChoice(userAnswer, correctAnswer);
            case TRUE_FALSE -> scoreTrueFalse(userAnswer, correctAnswer);
        };
    }

    private int scoreMultipleChoice(String userAnswer, String correctAnswer) {
        return userAnswer.equalsIgnoreCase(correctAnswer) ? 1 : 0;
    }

    private int scoreTrueFalse(String userAnswer, String correctAnswer) {
        return userAnswer.equalsIgnoreCase(correctAnswer) ? 1 : 0;
    }


}

