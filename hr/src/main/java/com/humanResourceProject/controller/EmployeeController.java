package com.humanResourceProject.controller;

import com.humanResourceProject.model.Employee;
import com.humanResourceProject.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees/")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam Optional<Integer> pageNo, @RequestParam Optional<Integer> pageSize) {
        List<Employee> employees = employeeService.getAllEmployees(pageNo.orElse(1), pageSize.orElse(10));
        return ResponseEntity.ok(employees);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("resting")
    public ResponseEntity<List<Employee>> getRestingEmployees(@RequestParam Optional<Integer> pageNo, @RequestParam Optional<Integer> pageSize) {
        List<Employee> restingEmployees = employeeService.getRestingEmployees(pageNo.orElse(1), pageSize.orElse(10));
        return ResponseEntity.ok(restingEmployees);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("non-resting")
    public ResponseEntity<List<Employee>> getNonRestingEmployees(@RequestParam Optional<Integer> pageNo, @RequestParam Optional<Integer> pageSize) {
        List<Employee> nonRestingEmployees = employeeService.getNonRestingEmployees(pageNo.orElse(1), pageSize.orElse(10));
        return ResponseEntity.ok(nonRestingEmployees);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping("{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("{employeeId}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee deleted.");
    }
}