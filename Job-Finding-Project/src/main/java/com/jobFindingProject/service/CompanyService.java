package com.jobFindingProject.service;

import com.jobFindingProject.model.Company;
import com.jobFindingProject.model.User;

import java.util.List;

public interface CompanyService {
    void createCompany(Company company);
    List<Company> getAllCompanies();
    List<User> filterEmployeesWithExperience(int yearsOfExperience);

}
