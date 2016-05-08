package com.fpj.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpj.spring.entities.EntEmployee;

public interface EmployeeRepository extends JpaRepository<EntEmployee, Integer> {

}
