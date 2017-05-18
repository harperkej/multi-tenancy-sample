package com.abidat.example.company.controller;

import com.abidat.example.company.dto.CompanyDto;
import com.abidat.example.company.service.CompanyService;
import com.abidat.example.configuration.TenantTracker;
import com.abidat.example.configuration.common.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by a.kuci on 5/12/2017.
 */
@RestController("companies")
public class CompanyController {


    @Autowired
    CompanyService companyService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public CompanyDto createNewCompany(@RequestBody CompanyDto companyDto) throws Exception {
        //The companies are stored in 'default' tenant..
        TenantTracker.setCurrentTenant(Constant.DEFAULT_TENANT);
        return this.companyService.createCompany(companyDto);
    }


    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompanyDto> getAll() {
        TenantTracker.setCurrentTenant(Constant.DEFAULT_TENANT);
        return this.companyService.getAll();
    }

}
