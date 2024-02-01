package com.jacinthsolution.quizscoring.repositories;

import com.jacinthsolution.quizscoring.entities.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {
}
