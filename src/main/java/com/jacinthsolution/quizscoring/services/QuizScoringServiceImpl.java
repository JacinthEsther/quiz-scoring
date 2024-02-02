package com.jacinthsolution.quizscoring.services;

import com.jacinthsolution.quizscoring.entities.QuestionType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizScoringServiceImpl implements QuizScoringService {

    public int scoreQuiz(List<String> userAnswers, List<String> correctAnswers, QuestionType questionType) {

        return switch (questionType) {
            case MULTIPLE_CHOICE -> scoreMultipleChoice(userAnswers, correctAnswers);
            case TRUE_FALSE -> scoreTrueFalse(userAnswers, correctAnswers);
        };
    }

    private int scoreMultipleChoice(List<String> userAnswers, List<String> correctAnswers) {

        return (int) userAnswers.stream().filter(correctAnswers::contains).count();
    }

    private int scoreTrueFalse(List<String> userAnswers, List<String> correctAnswers) {

        return (int) userAnswers.stream().filter(correctAnswers::contains).count();
    }
}

