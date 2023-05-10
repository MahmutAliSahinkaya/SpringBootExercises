package com.humanResourceProject.controller;

import com.humanResourceProject.model.Admin;
import com.humanResourceProject.model.Employee;
import com.humanResourceProject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register/employee")
    public ResponseEntity<String> registerEmployee(@RequestBody @Valid Employee employee) {
        authService.registerEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body("Employee registration successful.");
    }

    @PostMapping("register/admin")
    public ResponseEntity<String> registerAdmin(@RequestBody @Valid Admin admin) {
        authService.registerAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body("HR manager registration successful.");
    }
}
