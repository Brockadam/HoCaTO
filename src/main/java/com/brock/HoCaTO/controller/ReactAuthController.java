package com.brock.HoCaTO.controller;

import com.brock.HoCaTO.dto.UserDto;
import com.brock.HoCaTO.entity.LoginForm;
import com.brock.HoCaTO.entity.User;
import com.brock.HoCaTO.exceptionHandler.DuplicateEmailException;
import com.brock.HoCaTO.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") //This will need to change one day
@Slf4j
public class ReactAuthController {

    private UserService userService;

    public ReactAuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        // Validate the login credentials (e.g., check against the database)
        if (isValidLogin(loginForm.getEmail(), loginForm.getPassword())) {
            // Successful login
            return ResponseEntity.ok("Login successful");
        } else {
            // Invalid login
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registration(@Valid @RequestBody UserDto userDto, BindingResult result) {
        log.info("Attempting to register Account for " + userDto.getUsername());
        if (result.hasErrors()) {
            // Handle validation errors and return appropriate response
            return ResponseEntity.badRequest().body("Validation errors");
        }

        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            log.error("There is already an account registered with the same email");

            throw new DuplicateEmailException("Email already registered");
        }

        userService.saveUser(userDto);
        System.out.println("Registration successful");

        return ResponseEntity.ok("Registration successful");

    }

    private boolean isValidLogin(String email, String password) {
        // Implement your validation logic here
        // For demonstration purposes, you can use a simple hardcoded check
        return "user@example.com".equals(email) && "password123".equals(password);
    }
}

