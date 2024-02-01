package com.jacinthsolution.quizscoring.controller;

import com.jacinthsolution.quizscoring.dtos.ApiResponse;
import com.jacinthsolution.quizscoring.dtos.RegisterUserDto;
import com.jacinthsolution.quizscoring.services.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        try {
            String savedUser = userService.saveUser(registerUserDto);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedUser);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{email}")
    public ResponseEntity<?> retrieveACustomer(@PathVariable String email) {
        try {
            return new ResponseEntity<>(userService.retrieveACustomerBy(email), HttpStatus.FOUND);
        }  catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_FOUND);

        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> retrieveAllCustomers() {
        try {
            return new ResponseEntity<>(userService.retrieveAllCustomers(), HttpStatus.FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

//    @RequestMapping("/user")
//    public User getUserDetailsAfterLogin(Authentication authentication) {
//        List<User> customers = userRepository.findByEmail(authentication.getName());
//        if (customers.size() > 0) {
//            return customers.get(0);
//        } else {
//            return null;
//        }
//
//    }
}
