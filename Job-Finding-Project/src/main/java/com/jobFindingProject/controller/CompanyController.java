package com.jobFindingProject.controller;

import com.jobFindingProject.model.Company;
import com.jobFindingProject.model.User;
import com.jobFindingProject.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody Company company) {
        companyService.createCompany(company);
        return ResponseEntity.ok("Company created successfully.");
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<User>> filterEmployeesWithExperience(@RequestParam int yearsOfExperience) {
        List<User> employees = companyService.filterEmployeesWithExperience(yearsOfExperience);
        return ResponseEntity.ok(employees);
    }
}

