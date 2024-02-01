package com.jacinthsolution.quizscoring.repositories;

import com.jacinthsolution.quizscoring.entities.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
}
