package com.jacinthsolution.quizscoring.controller;

import com.jacinthsolution.quizscoring.dtos.QuizSubmissionDTO;
import com.jacinthsolution.quizscoring.dtos.UserQuizScoreDTO;
import com.jacinthsolution.quizscoring.exceptions.QuizValidationException;
import com.jacinthsolution.quizscoring.services.QuizServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@Validated
public class QuizController {

    @Autowired
    private QuizServiceImpl quizService;



    @PostMapping("/submit")
    public ResponseEntity<String> submitQuiz(@Valid @RequestBody QuizSubmissionDTO quizSubmission) {
        try {
            quizService.validateQuizSubmission(quizSubmission);

            int score = quizService.scoreQuiz(quizSubmission);
            quizService.saveUserQuizScore(quizSubmission.getUserId(), quizSubmission.getQuizId(), score);

            return ResponseEntity.ok("Quiz submitted successfully. Your score: " + score);
        } catch (QuizValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/scores/{userId}")
    public ResponseEntity<List<UserQuizScoreDTO>> getUserScores(@PathVariable Long userId) {
        try {
            List<UserQuizScoreDTO> userScores = quizService.getUserScores(userId);
            return ResponseEntity.ok(userScores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

