package com.jacinthsolution.quizscoring.services;

import java.util.Set;

public interface AuthorityService {
    void addAuthority(Long userId, String role);

    void addAuthorities(Long userId, Set<String> roles);
}
