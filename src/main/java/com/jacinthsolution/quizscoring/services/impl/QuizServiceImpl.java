package com.jacinthsolution.quizscoring.services.impl;

import com.jacinthsolution.quizscoring.dtos.*;
import com.jacinthsolution.quizscoring.entities.*;
import com.jacinthsolution.quizscoring.exceptions.QuizValidationException;
import com.jacinthsolution.quizscoring.repositories.QuizAnswerRepository;
import com.jacinthsolution.quizscoring.repositories.QuizQuestionRepository;
import com.jacinthsolution.quizscoring.repositories.UserQuizScoreRepository;
import com.jacinthsolution.quizscoring.services.QuizService;
import com.jacinthsolution.quizscoring.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizQuestionRepository questionRepository;

    @Autowired
    private UserQuizScoreRepository userQuizScoreRepository;

    @Autowired
    private QuizAnswerRepository quizAnswerRepository;

    @Autowired
    private QuizScoringServiceImpl scoringService;

    @Autowired
    private UserService userService;


    public List<QuizScoreDTO> scoreQuiz(Long userId, List<QuizSubmissionDTO> quizSubmissions) {
        List<QuizScoreDTO> quizScores = new ArrayList<>();

        int totalScore = 0;

        userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<Long> processedQuizIds = new HashSet<>();

        for (QuizSubmissionDTO quizSubmission : quizSubmissions) {
            Long quizId = quizSubmission.getQuizId();

            if (processedQuizIds.contains(quizId)) {
                continue;
            }

            QuizQuestion retrievedQuizQuestion = questionRepository.findById(quizId)
                    .orElseThrow(() -> new QuizValidationException("Quiz not found"));

            String userAnswer = quizSubmission.getUserAnswer();
            String retrievedCorrectAnswer = retrievedQuizQuestion.getCorrectAnswer().getCorrectAnswer();

            int scoredUserAnswer = scoringService.scoreQuiz(userAnswer, retrievedCorrectAnswer, retrievedQuizQuestion.getQuestionType());
            totalScore += scoredUserAnswer;

            processedQuizIds.add(quizId);
            quizScores.add(new QuizScoreDTO(retrievedQuizQuestion.getQuestion(), retrievedCorrectAnswer, scoredUserAnswer));
        }

        saveUserQuizScore(userId, totalScore, new ArrayList<>(processedQuizIds));
        return quizScores;

    }


    public void saveUserQuizScore(Long userId, int score, List<Long> quizId) {
        UserQuizScore userQuizScore = new UserQuizScore();
        userQuizScore.setUser(new User(userId));
        userQuizScore.setQuizQuestions(questionRepository.findAllById(quizId));
        userQuizScore.setScore(score);

        userQuizScoreRepository.save(userQuizScore);
    }

    public List<UserQuizScoreDTO> getUserResult(Long userId) {
        List<UserQuizScore> userQuizScores = userQuizScoreRepository.findByUserId(userId);


        return userQuizScores.stream()
                .map(this::mapToUserQuizScoreDTO)
                .collect(Collectors.toList());
    }

    private UserQuizScoreDTO mapToUserQuizScoreDTO(UserQuizScore userQuizScore) {

        UserQuizScoreDTO userQuizScoreDTO = new UserQuizScoreDTO();

        List<Long> quizIds = userQuizScore.getQuizQuestions().stream()
                .map(QuizQuestion::getId)
                .collect(Collectors.toList());

        userQuizScoreDTO.setQuizId(quizIds);
        userQuizScoreDTO.setScore(userQuizScore.getScore());

        return userQuizScoreDTO;
    }


    public int getTotalScore(Long userId) {
        List<UserQuizScore> userQuizScores = userQuizScoreRepository.findByUserId(userId);

        return userQuizScores.stream()
                .mapToInt(UserQuizScore::getScore)
                .sum();
    }


    public QuizQuestion createQuizQuestion(QuizQuestionDTO questionDTO) {

        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setQuestion(questionDTO.getQuestion());
        quizQuestion.setQuestionType(QuestionType.valueOf(questionDTO.getQuestionType().toUpperCase()));
        quizQuestion.setDifficultyLevel(DifficultyLevel.valueOf(questionDTO.getDifficultyLevel().toUpperCase()));


        return questionRepository.save(quizQuestion);
    }

    public QuizQuestion addAnswersToQuestion(Long questionId, QuizAnswerDTO answerDTO) {
        QuizQuestion quizQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuizValidationException("Quiz question not found"));


        quizQuestion.setPossibleAnswers(answerDTO.getPossibleAnswers());

        QuizAnswer correctAnswer = new QuizAnswer();
        correctAnswer.setQuizQuestion(quizQuestion);
        correctAnswer.setCorrectAnswer(answerDTO.getCorrectAnswer());

        QuizAnswer savedQuizAnswer = quizAnswerRepository.save(correctAnswer);
        quizQuestion.setCorrectAnswer(savedQuizAnswer);


        return questionRepository.save(quizQuestion);
    }


}

