package com.jacinthsolution.quizscoring.services;

import com.jacinthsolution.quizscoring.dtos.QuizAnswerDTO;
import com.jacinthsolution.quizscoring.dtos.QuizQuestionDTO;
import com.jacinthsolution.quizscoring.dtos.QuizSubmissionDTO;
import com.jacinthsolution.quizscoring.dtos.UserQuizScoreDTO;
import com.jacinthsolution.quizscoring.entities.*;
import com.jacinthsolution.quizscoring.exceptions.QuizScoringException;
import com.jacinthsolution.quizscoring.exceptions.QuizValidationException;
import com.jacinthsolution.quizscoring.repositories.QuizQuestionRepository;
import com.jacinthsolution.quizscoring.repositories.UserQuizScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizQuestionRepository questionRepository;

    @Autowired
    private UserQuizScoreRepository userQuizScoreRepository;

    @Autowired
    private QuizScoringServiceImpl scoringService;


    public void validateQuizSubmission(QuizSubmissionDTO quizSubmission) {
        QuizQuestion quizQuestion = questionRepository.findById(quizSubmission.getQuizId())
                .orElseThrow(() -> new QuizValidationException("Quiz not found"));


        if (quizSubmission.getUserAnswers().size() != quizQuestion.getAnswers().size()) {
            throw new QuizValidationException("Invalid number of answers submitted");
        }
    }

    public int scoreQuiz(QuizSubmissionDTO quizSubmission) {
        QuizQuestion quizQuestion = questionRepository.findById(quizSubmission.getQuizId())
                .orElseThrow(() -> new QuizScoringException("Quiz not found"));

        List<String> correctAnswers = quizQuestion.getAnswers().stream()
                .map(QuizAnswer::getAnswer)
                .collect(Collectors.toList());

        return scoringService.scoreQuiz(quizSubmission.getUserAnswers(), correctAnswers, quizQuestion.getQuestionType());
    }

    public void saveUserQuizScore(Long userId, Long quizId, int score) {
        UserQuizScore userQuizScore = new UserQuizScore();
        userQuizScore.setUser(new User(userId));
        userQuizScore.setQuizQuestion(questionRepository.getById(quizId));
        userQuizScore.setScore(score);

        userQuizScoreRepository.save(userQuizScore);
    }

    public List<UserQuizScoreDTO> getUserScores(Long userId) {
        List<UserQuizScore> userQuizScores = userQuizScoreRepository.findByUserId(userId);

        return userQuizScores.stream()
                .map(this::mapToUserQuizScoreDTO)
                .collect(Collectors.toList());
    }

    private UserQuizScoreDTO mapToUserQuizScoreDTO(UserQuizScore userQuizScore) {
        UserQuizScoreDTO userQuizScoreDTO = new UserQuizScoreDTO();
        userQuizScoreDTO.setQuizId(userQuizScore.getQuizQuestion().getId());
        userQuizScoreDTO.setScore(userQuizScore.getScore());


        return userQuizScoreDTO;
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

        QuizQuestion finalQuizQuestion = quizQuestion;
        List<QuizAnswer> answers = answerDTO.getAnswers().stream()
                .map(answerText -> {
                    QuizAnswer answer = new QuizAnswer();
                    answer.setAnswer(answerText);
                    answer.setQuizQuestion(finalQuizQuestion);
                    return answer;
                })
                .collect(Collectors.toList());

        quizQuestion.setAnswers(answers);
        quizQuestion = questionRepository.save(quizQuestion);

        quizQuestion.getAnswers().forEach(answer -> {
            if (answer.getAnswer().equals(answerDTO.getCorrectAnswer())) {
                answer.setCorrectAnswer(answerDTO.getCorrectAnswer());
            }
        });

        return questionRepository.save(quizQuestion);
    }






}

