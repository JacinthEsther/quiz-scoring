package com.jacinthsolution.quizscoring.services;

import com.jacinthsolution.quizscoring.dtos.QuizAnswerDTO;
import com.jacinthsolution.quizscoring.dtos.QuizQuestionDTO;
import com.jacinthsolution.quizscoring.dtos.QuizSubmissionDTO;
import com.jacinthsolution.quizscoring.dtos.UserQuizScoreDTO;
import com.jacinthsolution.quizscoring.entities.*;
import com.jacinthsolution.quizscoring.exceptions.QuizValidationException;
import com.jacinthsolution.quizscoring.repositories.QuizAnswerRepository;
import com.jacinthsolution.quizscoring.repositories.QuizQuestionRepository;
import com.jacinthsolution.quizscoring.repositories.UserQuizScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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


//todo validate that a question is marked once regardless of the number of time of submission


    public int scoreOneQuiz(Long userId,QuizSubmissionDTO quizSubmission) {
        userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        QuizQuestion quizQuestion = questionRepository.findById(quizSubmission.getQuizId())
                .orElseThrow(() -> new QuizValidationException("Quiz not found"));

        log.info("Here are the correct answers: " + quizQuestion.getCorrectAnswer().getCorrectAnswer());

        String correctAnswer = quizQuestion.getCorrectAnswer().getCorrectAnswer();

        int score = scoringService.scoreQuiz(quizSubmission.getUserAnswer(), correctAnswer, quizQuestion.getQuestionType());
        saveUserQuizScore(userId,score, Collections.singletonList(quizQuestion.getId()));
        return score;

    }

    public int scoreMultipleQuiz(Long userId,List<QuizSubmissionDTO> quizSubmissions) {
        int totalScore = 0;
        userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Long> quizIds = new ArrayList<>();

        for (QuizSubmissionDTO quizSubmission : quizSubmissions) {
            Long quizId = quizSubmission.getQuizId();
            QuizQuestion quizQuestion = questionRepository.findById(quizId)
                    .orElseThrow(() -> new QuizValidationException("Quiz not found"));

            String userAnswer = quizSubmission.getUserAnswer();
            String correctAnswer = quizQuestion.getCorrectAnswer().getCorrectAnswer();

            totalScore += scoringService.scoreQuiz(userAnswer, correctAnswer, quizQuestion.getQuestionType());
            quizIds.add(quizQuestion.getId());
        }
             saveUserQuizScore(userId,totalScore,quizIds);
        return totalScore;
    }


    public void saveUserQuizScore(Long userId, int score, List<Long>quizId) {
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

