package com.humanResourceProject.service;

import com.humanResourceProject.model.Admin;
import com.humanResourceProject.model.User;
import com.humanResourceProject.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class AdminService {
    private AdminRepository adminRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, UserService userService,
                        PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public void addAdmin(Admin admin) {
        log.info("Adding admin: {}", admin);

        User existingUser = this.userService.getUserByEmail(admin.getEmail());

        if (existingUser != null) {
            log.info("User with email {} already exists.", admin.getEmail());
            return;
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
    }

    public void deleteAdmin(Long adminId) {
        log.info("Deleting admin with ID: {}", adminId);

        adminRepository.deleteById(adminId);
    }
}

