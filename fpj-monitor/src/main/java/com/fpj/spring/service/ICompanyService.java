package com.fpj.spring.service;

import com.fpj.client.dtos.ICompanyDto;
import com.fpj.spring.dtos.CompanyPagingLoadResultBean;
import com.fpj.spring.entities.EntCompany;

public interface ICompanyService extends
		IGenericService<ICompanyDto, CompanyPagingLoadResultBean, EntCompany> {

	ICompanyDto getDto(EntCompany company);

}
