package com.jacinthsolution.quizscoring.services;

import com.jacinthsolution.quizscoring.dtos.QuizSubmissionDTO;
import com.jacinthsolution.quizscoring.dtos.UserQuizScoreDTO;

import java.util.List;

public interface QuizService {

    void validateQuizSubmission(QuizSubmissionDTO quizSubmission);
    int scoreQuiz(QuizSubmissionDTO quizSubmission);
    void saveUserQuizScore(Long userId, Long quizId, int score);
    List<UserQuizScoreDTO> getUserScores(Long userId);
}
