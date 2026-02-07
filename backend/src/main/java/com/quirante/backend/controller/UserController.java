package com.quirante.backend.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quirante.backend.entity.User;
import com.quirante.backend.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
    
    @Autowired
    private UserService userv;

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody User newUser) {
        try {
            User saved = userv.createUser(newUser);
            // hide password before returning
            saved.setPassword(null);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error creating user: " + e.getMessage());
        }
    }

    // LOGIN - sets HttpSession attribute and returns user (without password)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginData, HttpSession session) {
        try {
            String input = loginData.getEmail(); // can be email or fullname
            String password = loginData.getPassword();

            Optional<User> userOpt = userv.findByEmailOrFullname(input);

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(404).body("User not found");
            }

            User user = userOpt.get();

            // Plaintext password check (replace with hashed check in production)
            if (!userv.checkPassword(user, password)) {
                return ResponseEntity.status(401).body("Wrong password");
            }

            // Set session attributes
            session.setAttribute("userId", user.getId());
            session.setAttribute("userEmail", user.getEmail());

            // hide password before returning
            user.setPassword(null);
            return ResponseEntity.ok(user);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }
    

    // LOGOUT - invalidate session
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }
}
