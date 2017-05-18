package com.abidat.example.company.service;

import com.abidat.example.company.dto.CompanyDto;

import java.util.List;

/**
 * Created by a.kuci on 5/12/2017.
 */
public interface CompanyService {

    public CompanyDto createCompany(CompanyDto companyDto) throws Exception;

    public List<CompanyDto> getAll();

}
