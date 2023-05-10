package com.humanResourceProject.service;

import com.humanResourceProject.model.Admin;
import com.humanResourceProject.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private EmployeeService employeeService;
    private AdminService adminService;

    @Autowired
    public AuthService(EmployeeService employeeService, AdminService adminService) {
        this.employeeService = employeeService;
        this.adminService = adminService;
    }

    public void registerAdmin(Admin admin) {
        log.info("Registering HR manager: {}", admin);
        adminService.addAdmin(admin);
    }

    public void registerEmployee(Employee employee) {
        log.info("Registering employee: {}", employee);
        employeeService.addEmployee(employee);
    }
}


