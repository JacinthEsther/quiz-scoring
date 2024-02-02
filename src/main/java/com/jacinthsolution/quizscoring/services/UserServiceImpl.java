package com.jacinthsolution.quizscoring.services;

import com.jacinthsolution.quizscoring.dtos.RegisterUserDto;
import com.jacinthsolution.quizscoring.entities.User;
import com.jacinthsolution.quizscoring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public String saveUser(RegisterUserDto registerUserDto) {
        User user = new User();
        user.setEmail(registerUserDto.getEmail());
        user.setCreateDate(String.valueOf(new Date(System.currentTimeMillis())));
        String hashPwd = passwordEncoder.encode(registerUserDto.getPassword());
        user.setPassword(hashPwd);

        user.setRole(registerUserDto.getRole().toUpperCase());
        User save = userRepository.save(user);

        return "user with " + save.getEmail() + " registered successfully";

    }


    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @PreAuthorize("hasRole('ADMIN') or true")
    public boolean isUserAdmin(String email, Authentication authentication) {
        // Retrieve the user from the database based on the email
        List<User> users = userRepository.findByEmail(email);
        // Check if the user exists and has the 'ADMIN' role
        System.out.println(users.size());
        return users.stream().anyMatch(user -> user.getRole().contains("ADMIN"));
        }


    @Override
    public List<User> retrieveACustomerBy(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> retrieveAllCustomers() {


        return userRepository.findAll();
    }
}
