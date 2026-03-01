package com.job.app.company;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface CompanyService {
    List<Company> getAllCompanies();

    Company getCompanyById(Long id);

    void createCompay(Company company);

    Company updateCompanyById(Long id, Company company);

    boolean deleteCompany(Long id);
}
