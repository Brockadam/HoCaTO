package com.brock.HoCaTO.controller;

import com.brock.HoCaTO.dto.UserDto;
import com.brock.HoCaTO.entity.LoginForm;
import com.brock.HoCaTO.entity.User;
import com.brock.HoCaTO.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api")
public class ReactAuthController {

    private UserService userService;

    public ReactAuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public RedirectView login(@RequestBody LoginForm loginForm) {
        // Validate the login credentials (e.g., check against the database)
        if (isValidLogin(loginForm.getEmail(), loginForm.getPassword())) {
            // Successful login
            return new RedirectView("/users"); // Redirect to the "users" page
        } else {
            // Invalid login
            return new RedirectView("/login"); // Redirect back to login page with an error message
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registration(@Valid @RequestBody UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            // Handle validation errors and return appropriate response
            return ResponseEntity.badRequest().body("Validation errors");
        }

        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("There is already an account registered with the same email");
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

