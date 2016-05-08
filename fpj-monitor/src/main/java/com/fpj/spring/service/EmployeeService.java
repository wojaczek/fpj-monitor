package com.fpj.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.fpj.client.IEmployeeDto;
import com.fpj.spring.dtos.EmployeeDto;
import com.fpj.spring.dtos.EmployeePagingLoadResultBean;
import com.fpj.spring.entities.EntEmployee;
import com.fpj.spring.repository.CompanyRepository;
import com.fpj.spring.repository.EmployeeRepository;
@Service
public class EmployeeService extends GenericService<IEmployeeDto, EmployeePagingLoadResultBean, EntEmployee> {
	@Autowired
	EmployeeRepository repository;
	@Autowired
	CompanyService companyService;
	@Autowired
	CompanyRepository companyRepository;
	
	@Override
	protected void fillEntity(EntEmployee entity, IEmployeeDto dto) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setVisaExpiredDate(dto.getVisaExpiredDate());
		if (dto.getCompany()!=null && dto.getCompany().getId()!=null){
			entity.setCompany(companyRepository.getOne(dto.getCompany().getId()));
		}
	}

	@Override
	protected EntEmployee createEntity() {
		return new EntEmployee();
	}

	@Override
	protected IEmployeeDto getDto(EntEmployee entity) {
		IEmployeeDto dto = new EmployeeDto();
		dto.setId(entity.getId());
		dto.setCompany(entity.getCompany() == null? null : companyService.getDto(entity.getCompany()));
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setVisaExpiredDate(entity.getVisaExpiredDate());
		return dto;
	}

	@Override
	protected EmployeePagingLoadResultBean createResult() {
		return new EmployeePagingLoadResultBean();
	}

	@Override
	protected JpaRepository<EntEmployee, Integer> getRepository() {
		return repository;
	}

}
