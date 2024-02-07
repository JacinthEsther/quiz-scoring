package com.jacinthsolution.quizscoring.controller;

import com.jacinthsolution.quizscoring.dtos.*;
import com.jacinthsolution.quizscoring.entities.QuizQuestion;
import com.jacinthsolution.quizscoring.exceptions.QuizValidationException;
import com.jacinthsolution.quizscoring.services.impl.QuizServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@Validated
public class QuizController {

    @Autowired
    private QuizServiceImpl quizService;


    @PostMapping("{userId}/submit/quiz")
    public ResponseEntity<?> submitQuiz(@Valid @RequestBody List<QuizSubmissionDTO> quizSubmission,
                                        @PathVariable Long userId) {
        try {

            List<QuizScoreDTO> score = quizService.scoreQuiz(userId, quizSubmission);
            return new ResponseEntity<>(score, HttpStatus.FOUND);
        } catch (QuizValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/scores/{userId}")
    public ResponseEntity<?> getUserScores(@PathVariable Long userId) {
        try {
            List<UserQuizScoreDTO> userScores = quizService.getUserResult(userId);
            return ResponseEntity.ok(userScores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/total-score")
    public ResponseEntity<?> getTotalScore(@PathVariable Long userId) {
        try {
            int totalScore = quizService.getTotalScore(userId);
            return ResponseEntity.ok(totalScore);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("{email}/createQuestion")
    @PreAuthorize("@userServiceImpl.isUserAdmin(#email, authentication)")
    public ResponseEntity<QuizQuestion> createQuizQuestion(@RequestBody QuizQuestionDTO questionDTO,
                                                           @PathVariable String email) {
        QuizQuestion createdQuestion = quizService.createQuizQuestion(questionDTO);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    @PostMapping("{email}/addAnswers/{questionId}")
    @PreAuthorize("@userServiceImpl.isUserAdmin(#email, authentication)")
    public ResponseEntity<?> addAnswersToQuestion(
            @PathVariable String email,
            @PathVariable Long questionId,
            @RequestBody QuizAnswerDTO answerDTOList) {
        QuizQuestion quizQuestionWithAnswer = quizService.addAnswersToQuestion(questionId, answerDTOList);
        return new ResponseEntity<>(quizQuestionWithAnswer, HttpStatus.OK);
    }
}

