package org.example.sachi.controller;

import org.example.sachi.dto.LoginDTO;
import org.example.sachi.entity.User;
import org.example.sachi.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, String>> userLogin(@RequestBody LoginDTO loginDto) {
        Optional<User> optionalUser = userRepo.findByUserName(loginDto.getUserName());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Compare the plain text password with the encoded password
            if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                // Return a JSON response with the login message
                Map<String, String> response = new HashMap<>();
                response.put("message", "Login successful");
                return ResponseEntity.ok(response);
            }
        }

        // Return a JSON response for invalid credentials
        Map<String, String> response = new HashMap<>();
        response.put("message", "Invalid credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}
