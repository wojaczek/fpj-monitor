package com.fpj.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpj.spring.entities.EntCompany;

public interface CompanyRepository extends JpaRepository<EntCompany, Integer> {

}
