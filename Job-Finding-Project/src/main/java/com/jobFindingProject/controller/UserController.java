package com.jobFindingProject.controller;

import com.jobFindingProject.model.User;
import com.jobFindingProject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/jobseekers")
    public ResponseEntity<List<User>> filterJobSeekersWithExperience(@RequestParam int yearsOfExperience) {
        List<User> jobSeekers = userService.filterJobSeekersWithExperience(yearsOfExperience);
        return ResponseEntity.ok(jobSeekers);
    }
}

