package com.jacinthsolution.quizscoring.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AuthorityDTO {
    private Long userId;

    @Pattern(regexp = "^(?i)(user|admin|superUser)$", message = "Role should be 'user' or 'admin' or 'superUser")
    private String role;
}
