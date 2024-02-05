package com.jacinthsolution.quizscoring.services;

import com.jacinthsolution.quizscoring.dtos.QuizAnswerDTO;
import com.jacinthsolution.quizscoring.dtos.QuizQuestionDTO;
import com.jacinthsolution.quizscoring.dtos.QuizSubmissionDTO;
import com.jacinthsolution.quizscoring.dtos.UserQuizScoreDTO;
import com.jacinthsolution.quizscoring.entities.QuizQuestion;

import java.util.List;

public interface QuizService {

    void validateQuizSubmission(QuizSubmissionDTO quizSubmission);
    int scoreQuiz(QuizSubmissionDTO quizSubmission);
    void saveUserQuizScore(Long userId, Long quizId, int score);
    List<UserQuizScoreDTO> getUserScores(Long userId);
    QuizQuestion createQuizQuestion(QuizQuestionDTO questionDTO);
    QuizQuestion addAnswersToQuestion(Long questionId, QuizAnswerDTO answerDTOList);
}
