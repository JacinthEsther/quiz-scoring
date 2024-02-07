package com.jacinthsolution.quizscoring.services;

import com.jacinthsolution.quizscoring.dtos.*;
import com.jacinthsolution.quizscoring.entities.QuizQuestion;

import java.util.List;

public interface QuizService {


    void saveUserQuizScore(Long userId, int score, List<Long> quizId);

    List<UserQuizScoreDTO> getUserResult(Long userId);

    QuizQuestion createQuizQuestion(QuizQuestionDTO questionDTO);

    QuizQuestion addAnswersToQuestion(Long questionId, QuizAnswerDTO answerDTOList);

    List<QuizScoreDTO> scoreQuiz(Long userId, List<QuizSubmissionDTO> quizSubmissions);

    int getTotalScore(Long userId);
}
