package com.jacinthsolution.quizscoring.services;

import com.jacinthsolution.quizscoring.dtos.QuizAnswerDTO;
import com.jacinthsolution.quizscoring.dtos.QuizQuestionDTO;
import com.jacinthsolution.quizscoring.dtos.QuizSubmissionDTO;
import com.jacinthsolution.quizscoring.dtos.UserQuizScoreDTO;
import com.jacinthsolution.quizscoring.entities.QuizQuestion;

import java.util.List;

public interface QuizService {


    int scoreOneQuiz(Long userId,QuizSubmissionDTO quizSubmission);
    void saveUserQuizScore(Long userId, int score, List<Long>quizId);
    List<UserQuizScoreDTO> getUserResult(Long userId);
    QuizQuestion createQuizQuestion(QuizQuestionDTO questionDTO);
    QuizQuestion addAnswersToQuestion(Long questionId, QuizAnswerDTO answerDTOList);
    int scoreMultipleQuiz(Long userId,List<QuizSubmissionDTO> quizSubmissions);

    int getTotalScore(Long userId);
}
