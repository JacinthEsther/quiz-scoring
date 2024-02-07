package com.jacinthsolution.quizscoring.services;

import com.jacinthsolution.quizscoring.dtos.RegisterUserDto;
import com.jacinthsolution.quizscoring.entities.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface UserService {

    String saveUser(RegisterUserDto request);

    User retrieveACustomerBy(String email);

    boolean isUserAdmin(String email, Authentication authentication);

    List<User> retrieveAllCustomers();

    Optional<User> findById(Long userId);
}
