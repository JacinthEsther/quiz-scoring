package com.jacinthsolution.quizscoring.controller;

import com.jacinthsolution.quizscoring.dtos.ApiResponse;
import com.jacinthsolution.quizscoring.dtos.RegisterUserDto;
import com.jacinthsolution.quizscoring.services.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

            return new ResponseEntity<>
                    (new ApiResponse(true, savedUser), HttpStatus.CREATED);


        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{email}")
    public ResponseEntity<?> retrieveACustomer(@PathVariable String email) {
        try {
            return new ResponseEntity<>(userService.retrieveACustomerBy(email), HttpStatus.FOUND);
        }  catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("{email}/getAll")
    @PreAuthorize("@userServiceImpl.isUserAdmin(#email, authentication)")
    public ResponseEntity<?> retrieveAllCustomers(@PathVariable String email){
        try {
            return new ResponseEntity<>(userService.retrieveAllCustomers(), HttpStatus.FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
