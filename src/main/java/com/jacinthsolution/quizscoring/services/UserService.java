package com.jacinthsolution.quizscoring.services;

import com.jacinthsolution.quizscoring.dtos.RegisterUserDto;
import com.jacinthsolution.quizscoring.entities.User;

import java.util.List;

public interface UserService {

    String saveUser(RegisterUserDto request);

    void deleteAll();

    long count();

    List<User> retrieveACustomerBy(String email);

    List<User> retrieveAllCustomers();
}
