package com.jacinthsolution.quizscoring.repositories;

import com.jacinthsolution.quizscoring.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
