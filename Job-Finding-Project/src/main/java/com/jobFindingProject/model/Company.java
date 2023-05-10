package com.jobFindingProject.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "company")
    private List<User> employees;

    public Company(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
    }

    public void addEmployee(User employee) {
        employees.add(employee);
    }

    public List<User> filterEmployeesWithExperience(int yearsOfExperience) {
        List<User> filteredEmployees = new ArrayList<>();
        for (User employee : employees) {
            if (employee.getJobExperience() >= yearsOfExperience) {
                filteredEmployees.add(employee);
            }
        }
        return filteredEmployees;
    }
}
