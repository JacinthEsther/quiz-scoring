package com.jacinthsolution.quizscoring.repositories;

import com.jacinthsolution.quizscoring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
}
