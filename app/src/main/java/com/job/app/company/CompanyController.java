package com.job.app.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping()
    public ResponseEntity<List<Company>> getAllCompanies(){
        return ResponseEntity.ok( companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable Long id){
        Company company = companyService.getCompanyById(id);
        if(company!=null){
            return ResponseEntity.ok(company);
        }
        return new ResponseEntity<>("Id not found",HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<String> createCompany(@RequestBody Company company){
        companyService.createCompay(company);
        return new ResponseEntity<>("Company added successfully",HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestBody Company company){
        Company updated = companyService.updateCompanyById(id,company);
        if(updated!=null){
            return ResponseEntity.ok(company);
        }
        return new ResponseEntity<>("Id not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){
        boolean deleted= companyService.deleteCompany(id);
        if(deleted) {
            return new ResponseEntity<>("Company successfully deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Id does not exist",HttpStatus.NOT_FOUND);
    }

}
