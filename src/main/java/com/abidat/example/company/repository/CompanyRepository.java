package com.abidat.example.company.repository;

import com.abidat.example.company.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by a.kuci on 5/12/2017.
 */
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
}
