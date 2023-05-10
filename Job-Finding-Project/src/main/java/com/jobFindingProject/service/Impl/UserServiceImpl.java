package com.jobFindingProject.service.Impl;

import com.jobFindingProject.model.User;
import com.jobFindingProject.repository.UserRepository;
import com.jobFindingProject.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User user) {
        userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public List<User> filterJobSeekersWithExperience(int yearsOfExperience) {
        List<User> jobSeekers = userRepository.findAll();
        List<User> filteredJobSeekers = new ArrayList<>();
        for (User jobSeeker : jobSeekers) {
            if (jobSeeker.getWorkExperience() >= yearsOfExperience) {
                filteredJobSeekers.add(jobSeeker);
            }
        }
        return filteredJobSeekers;
    }
}

