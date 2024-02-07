package com.jacinthsolution.quizscoring.controller;

import com.jacinthsolution.quizscoring.dtos.AuthorityDTO;
import com.jacinthsolution.quizscoring.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/authorities")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @PostMapping("/add")
    public ResponseEntity<String> addAuthority(@RequestBody AuthorityDTO authorityDTO) {
        authorityService.addAuthority(authorityDTO.getUserId(), authorityDTO.getRole());
        return ResponseEntity.ok("Authority added successfully.");
    }

    @PostMapping("/users/{userId}/authorities")
    public ResponseEntity<String> addAuthorities(@RequestParam Long userId, @RequestBody Set<String> roles) {
        try {
            authorityService.addAuthorities(userId, roles);
            return ResponseEntity.ok("Authorities added successfully for user with ID: " + userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add authorities: " + e.getMessage());
        }
    }
}

