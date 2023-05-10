package com.jobFindingProject.service.Impl;

import com.jobFindingProject.model.Company;
import com.jobFindingProject.model.User;
import com.jobFindingProject.repository.CompanyRepository;
import com.jobFindingProject.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public List<User> filterEmployeesWithExperience(int yearsOfExperience) {
        List<Company> companies = companyRepository.findAll();
        List<User> filteredEmployees = new ArrayList<>();
        for (Company company : companies) {
            List<User> employees = company.filterEmployeesWithExperience(yearsOfExperience);
            filteredEmployees.addAll(employees);
        }
        return filteredEmployees;
    }
}

