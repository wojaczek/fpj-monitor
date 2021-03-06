package com.fpj.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpj.client.dtos.ICompanyDto;
import com.fpj.spring.dtos.CompanyPagingLoadResultBean;
import com.fpj.spring.exception.NotFoundException;
import com.fpj.spring.service.ICompanyService;
import com.fpj.spring.service.IGenericService;
@Controller
@RequestMapping("company")
public class CompanyController extends GenericController<ICompanyDto, CompanyPagingLoadResultBean>{

	@Autowired
	ICompanyService service;
	
	@Override
	protected IGenericService<ICompanyDto, CompanyPagingLoadResultBean, ?> getService() {
		return service;
	}

	@RequestMapping(value = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ICompanyDto saveChanges(@RequestBody ICompanyDto dto) throws NotFoundException{
		return super.save(dto);
	}
	
}
