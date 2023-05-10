package com.humanResourceProject.service;


import com.humanResourceProject.model.Employee;
import com.humanResourceProject.model.User;
import com.humanResourceProject.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, UserService userService,
                           PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Employee> getAllEmployees(int pageNo, int pageSize) {
        log.info("Retrieving all employees");

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        return employeeRepository.findAll(pageable).getContent();
    }

    public List<Employee> getRestingEmployees(int pageNo, int pageSize) {
        log.info("Retrieving resting employees");

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        return employeeRepository.getRestedUsers(pageable);
    }

    public List<Employee> getNonRestingEmployees(int pageNo, int pageSize) {
        log.info("Retrieving non-resting employees");

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        return employeeRepository.getNotRestedUsers(pageable);
    }

    public Employee getEmployeeById(Long employeeId) {
        log.info("Retrieving employee by ID: {}", employeeId);

        return employeeRepository.getById(employeeId);
    }

    public void addEmployee(Employee employee) {
        User existingUser = userService.getUserByEmail(employee.getEmail());

        if (existingUser != null) {
            log.info("User with email {} already exists", employee.getEmail());
            return;
        }

        employeeRepository.save(employee);
        log.info("Employee added: {}", employee);
    }

    public void deleteEmployee(Long employeeId) {
        log.info("Deleting employee with ID: {}", employeeId);

        employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeRepository.save(employee);
        log.info("Updating employee: {}", employee);
    }
}