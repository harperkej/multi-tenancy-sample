package com.abidat.example.company.service;

import com.abidat.example.company.dto.CompanyDto;
import com.abidat.example.company.entity.CompanyEntity;
import com.abidat.example.company.repository.CompanyRepository;
import com.abidat.example.configuration.datasource.DataSourceHolder;
import com.abidat.example.schema.DatabaseSchemaUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a.kuci on 5/12/2017.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    DataSourceHolder dataSourceHolder;

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) throws Exception {
        if (companyDto == null) {
            //TODO throw a good exception here!
        } else {
            CompanyEntity companyEntity = new CompanyEntity();
            companyEntity.setAddress(companyDto.getAddress());
            companyEntity.setCompanyName(companyDto.getCompanyName());
            companyEntity.setCompanyNumber(companyDto.getCompanyNumber().replace(" ", ""));
            //try to persist the company -> in this case the tenant!
            this.companyRepository.save(companyEntity);
            companyDto.setId(companyEntity.getId());
            dataSourceHolder.createNewDataSource(companyDto.getCompanyNumber().replace(" ", ""));
            //Generate the schema based on Company number, which is unique!
            DatabaseSchemaUtilities.createSchema(companyEntity.getCompanyNumber());

            companyDto.setCompanyNumber(companyDto.getCompanyNumber().replace(" ", ""));

        }
        return companyDto;
    }

    @Override
    public List<CompanyDto> getAll() {
        List<CompanyDto> res = null;
        List<CompanyEntity> foundCompanies = this.companyRepository.findAll();
        if (foundCompanies != null) {
            res = new ArrayList<>();
            foundCompanies.stream().map(CompanyServiceImpl::convertDto).forEach(res::add);
        }
        return res;
    }

    private static CompanyDto convertDto(CompanyEntity companyEntity) {
        CompanyDto res = null;
        if (companyEntity != null) {
            res = new CompanyDto();
            res.setId(companyEntity.getId());
            res.setCompanyNumber(companyEntity.getCompanyNumber());
            res.setAddress(companyEntity.getAddress());
            res.setCompanyName(companyEntity.getCompanyName());
        }
        return res;
    }

}
