package com.jacinthsolution.quizscoring.repositories;

import com.jacinthsolution.quizscoring.entities.UserQuizScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserQuizScoreRepository extends JpaRepository<UserQuizScore, Long> {
    List<UserQuizScore> findByUserId(Long userId);
}