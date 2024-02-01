package com.jacinthsolution.quizscoring.dtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class RegisterUserDto {
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "Invalid email format")
    private String email;

    @Size(min = 4, message = "Password should be at least 4 characters long")
    private String password;

    @Pattern(regexp = "^(user|admin)$", message = "Role should be 'user' or 'admin'")
    private String role;
}
