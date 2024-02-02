package com.jacinthsolution.quizscoring.services;

import com.jacinthsolution.quizscoring.entities.QuestionType;

import java.util.List;

public interface QuizScoringService {

    int scoreQuiz(List<String> userAnswers, List<String> correctAnswers, QuestionType questionType);
}
