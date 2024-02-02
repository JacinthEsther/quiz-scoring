package com.jacinthsolution.quizscoring.services;

import com.jacinthsolution.quizscoring.dtos.QuizSubmissionDTO;
import com.jacinthsolution.quizscoring.dtos.UserQuizScoreDTO;
import com.jacinthsolution.quizscoring.entities.QuizAnswer;
import com.jacinthsolution.quizscoring.entities.QuizQuestion;
import com.jacinthsolution.quizscoring.entities.User;
import com.jacinthsolution.quizscoring.entities.UserQuizScore;
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


}

