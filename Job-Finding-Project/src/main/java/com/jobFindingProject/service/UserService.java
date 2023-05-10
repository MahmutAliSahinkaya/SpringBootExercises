package com.jobFindingProject.service;

import com.jobFindingProject.model.User;

import java.util.List;

public interface UserService {
    void register(User user);
    User login(String username, String password);
    List<User> filterJobSeekersWithExperience(int yearsOfExperience);

}
