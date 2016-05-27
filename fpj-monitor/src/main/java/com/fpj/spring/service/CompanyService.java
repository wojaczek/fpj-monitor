package com.fpj.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpj.client.dtos.ICompanyDto;
import com.fpj.spring.dtos.CompanyDto;
import com.fpj.spring.dtos.CompanyPagingLoadResultBean;
import com.fpj.spring.entities.EntCompany;
import com.fpj.spring.repository.CompanyRepository;

	@Service
	public class CompanyService extends GenericService<ICompanyDto, CompanyPagingLoadResultBean, EntCompany> implements ICompanyService{
	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public ICompanyDto getDto(EntCompany entity) {
		ICompanyDto dto = new CompanyDto();
		dto.setId(entity.getId());
		dto.setCompanyName(entity.getCompanyName());
		return dto;
	}

	@Override
	protected CompanyPagingLoadResultBean createResult() {
		return new CompanyPagingLoadResultBean();
	}

	@Override
	protected CompanyRepository getRepository() {
		return companyRepository;
	}

	@Override
	protected void fillEntity(EntCompany entity, ICompanyDto dto) {
		entity.setCompanyName(dto.getCompanyName());
	}

	@Override
	protected EntCompany createEntity() {
		return new EntCompany();
	}

}
