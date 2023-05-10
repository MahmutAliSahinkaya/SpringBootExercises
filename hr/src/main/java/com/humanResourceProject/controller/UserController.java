package com.humanResourceProject.controller;

import com.humanResourceProject.model.User;
import com.humanResourceProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("all")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam Optional<Integer> pageNo, @RequestParam Optional<Integer> pageSize) {
        List<User> users = userService.getAllUsers(pageNo.orElse(1), pageSize.orElse(10));
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping("{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("{userId}")
    public ResponseEntity<String> deleteById(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted.");
    }
}