package com.jacinthsolution.quizscoring.services;

import com.jacinthsolution.quizscoring.entities.QuestionType;

public interface QuizScoringService {

    int scoreQuiz(String userAnswers, String correctAnswers, QuestionType questionType);

}
