package com.jacinthsolution.quizscoring.services.impl;

import com.jacinthsolution.quizscoring.entities.Authority;
import com.jacinthsolution.quizscoring.entities.User;
import com.jacinthsolution.quizscoring.repositories.AuthorityRepository;
import com.jacinthsolution.quizscoring.services.AuthorityService;
import com.jacinthsolution.quizscoring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void addAuthority(Long userId, String role) {
        User user = userService.findById(userId).orElseThrow(
                () -> new RuntimeException("user not found")

        );
        Authority authority = new Authority(role.toUpperCase(), user);
        authorityRepository.save(authority);
    }

    @Transactional
    public void addAuthorities(Long userId, Set<String> roles) {
        User user = userService.findById(userId).orElseThrow(
                () -> new RuntimeException("user not found")

        );

        Set<Authority> authorities = new HashSet<>();
        for (String role : roles) {
            authorities.add(new Authority(role.toUpperCase(), user));
        }
        authorityRepository.saveAll(authorities);
    }
}

