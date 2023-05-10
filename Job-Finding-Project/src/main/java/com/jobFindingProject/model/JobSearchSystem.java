package com.jobFindingProject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobSearchSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private List<User> jobSeekers;

    public void registerUser(User user) {
        jobSeekers.add(user);
    }

    public User login(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    private User getUserByUsername(String username) {
        for (User user : jobSeekers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public List<User> filterJobSeekersWithExperience(int yearsOfExperience) {
        List<User> filteredJobSeekers = new ArrayList<>();
        for (User jobSeeker : jobSeekers) {
            if (jobSeeker.getWorkExperience() >= yearsOfExperience) {
                filteredJobSeekers.add(jobSeeker);
            }
        }
        return filteredJobSeekers;
    }
}
