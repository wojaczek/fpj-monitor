package com.fpj.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fpj.client.ICompanyDto;
import com.fpj.spring.dtos.CompanyPagingResultBean;
import com.fpj.spring.service.CompanyService;
import com.fpj.spring.service.GenericService;
@Controller
@RequestMapping("company")
public class CompanyController extends GenericController<ICompanyDto, CompanyPagingResultBean>{

	@Autowired
	CompanyService service;
	
	@Override
	protected GenericService<ICompanyDto, CompanyPagingResultBean, ?> getService() {
		return service;
	}

}
