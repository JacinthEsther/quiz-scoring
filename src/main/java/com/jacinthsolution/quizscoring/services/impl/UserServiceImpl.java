package com.jacinthsolution.quizscoring.services.impl;

import com.jacinthsolution.quizscoring.dtos.RegisterUserDto;
import com.jacinthsolution.quizscoring.entities.User;
import com.jacinthsolution.quizscoring.repositories.UserRepository;
import com.jacinthsolution.quizscoring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public String saveUser(RegisterUserDto registerUserDto) {
        User user = new User();

        boolean userExist = checkThatUserDoesNotExist(registerUserDto.getEmail());

        if(userExist){
            return "user has been registered";
        }

        user.setEmail(registerUserDto.getEmail());
        user.setCreateDate(String.valueOf(new Date(System.currentTimeMillis())));
        String hashPwd = passwordEncoder.encode(registerUserDto.getPassword());
        user.setPassword(hashPwd);

        user.setRole("USER");

        User savedUser = userRepository.save(user);

        return "user with " + savedUser.getEmail() + " registered successfully";

    }

    private boolean checkThatUserDoesNotExist(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.isPresent();
    }


    @PreAuthorize("hasAuthority('ADMIN') or true")
    public boolean isUserAdmin(String email, Authentication authentication) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found")

        );
        if (user != null) {
            return user.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
        } else {
            return false;
        }
    }


    @Override
    public User retrieveACustomerBy(String email) {

        return userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> retrieveAllCustomers() {


        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
}
